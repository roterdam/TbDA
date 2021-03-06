package dk.brics.tajs.analysis.nativeobjects;

import java.util.Set;

import dk.brics.tajs.analysis.Conversion;
import dk.brics.tajs.analysis.InitialStateBuilder;
import dk.brics.tajs.analysis.NativeFunctions;
import dk.brics.tajs.analysis.Solver;
import dk.brics.tajs.analysis.State;
import dk.brics.tajs.analysis.FunctionCalls.CallInfo;
import dk.brics.tajs.dependency.Dependency;
import dk.brics.tajs.dependency.graph.DependencyGraphReference;
import dk.brics.tajs.dependency.graph.DependencyNode;
import dk.brics.tajs.dependency.graph.Label;
import dk.brics.tajs.dependency.graph.nodes.DependencyExpressionNode;
import dk.brics.tajs.flowgraph.Node;
import dk.brics.tajs.flowgraph.ObjectLabel;
import dk.brics.tajs.flowgraph.ObjectLabel.Kind;
import dk.brics.tajs.lattice.Value;

/**
 * 15.2 native Object functions.
 */
public class JSObject {

	private JSObject() {
	}

	/**
	 * Evaluates the given native function.
	 */
	public static Value evaluate(ECMAScriptObjects nativeobject, CallInfo<? extends Node> call, State state, Solver.SolverInterface c) {
		if (nativeobject != ECMAScriptObjects.OBJECT)
			if (NativeFunctions.throwTypeErrorIfConstructor(call, state, c))
				return Value.makeBottom(new Dependency(), new DependencyGraphReference());

		switch (nativeobject) {

		case OBJECT: { // 15.2.1 and 15.2.2
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################

			// ==================================================
			DependencyExpressionNode node = DependencyNode.link(Label.CALL, call.getSourceNode(), state);
			// ==================================================

			NativeFunctions.expectParameters(nativeobject, call, c, 0, 1);
			Value arg = NativeFunctions.readParameter(call, 0);

			// ##################################################
			dependency.join(arg.getDependency());
			// ##################################################

			// ==================================================
			node.addParent(arg);
			// ==================================================

			Value res;
			if (call.isConstructorCall()) {
				// 15.2.2.1 step 3
				res = arg.restrictToObject();
				if (!arg.isNotStr() || !arg.isNotBool() || !arg.isNotNum()) {
					// 15.2.2.1 step 5-7
					res = res.join(Conversion.toObject(state, call.getSourceNode(), arg.restrictToStrBoolNum(), c)); // TODO:
																														// disable
																														// messages
																														// for
																														// this
																														// call
																														// to
																														// toObject?
				}
			} else {
				// 15.2.1.1
				res = Conversion.toObject(state, call.getSourceNode(), arg.restrictToNotNullNotUndef(), c); // TODO:
																											// disable
																											// messages
																											// for
																											// this
																											// call
																											// to
																											// toObject?
			}
			if (arg.isMaybeNull() || arg.isMaybeUndef()) {
				// 15.2.2.1 step 8
				ObjectLabel obj = new ObjectLabel(call.getSourceNode(), Kind.OBJECT);
				state.newObject(obj);
				state.writeInternalPrototype(obj, Value.makeObject(InitialStateBuilder.OBJECT_PROTOTYPE, dependency, node.getReference()));
				res = res.joinObject(obj);
			}
			return res;
		}

		case OBJECT_TOSTRING: // 15.2.4.2
		case OBJECT_TOLOCALESTRING: { // 15.2.4.3
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################

			// ==================================================
			DependencyExpressionNode node = DependencyNode.link(Label.CALL, call.getSourceNode(), state);
			// ==================================================

			NativeFunctions.expectParameters(nativeobject, call, c, 0, 0);
			Value propval = Conversion.toString(NativeFunctions.readParameter(call, 0), c);
			Value thisval = state.readInternalValue(state.readThisObjects());

			// ##################################################
			dependency.join(thisval.getDependency());
			dependency.join(propval.getDependency());
			// ##################################################

			// ==================================================
			node.addParent(thisval);
			node.addParent(propval);
			// ==================================================

			Set<ObjectLabel> thisobj = state.readThisObjects();
			if (thisobj.size() == 1)
				return Value.makeStr("[object " + thisobj.iterator().next().getKind() + "]", dependency, node.getReference());
			else
				return Value.makeAnyStr(dependency, node.getReference()); // TODO:
																						// better
			// approximation of
			// string sets?
		}

		case OBJECT_VALUEOF: { // 15.2.4.4
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################

			// ==================================================
			DependencyExpressionNode node = DependencyNode.link(Label.CALL, call.getSourceNode(), state);
			// ==================================================

			NativeFunctions.expectParameters(nativeobject, call, c, 0, 0);
			Value thisval = state.readInternalValue(state.readThisObjects());

			// ##################################################
			dependency.join(thisval.getDependency());
			// ##################################################

			// ==================================================
			node.addParent(thisval);
			// ==================================================

			return Value.makeObject(state.readThisObjects(), dependency, node.getReference());
		}

		case OBJECT_HASOWNPROPERTY: { // 15.2.4.5
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################

			// ==================================================
			DependencyExpressionNode node = DependencyNode.link(Label.CALL, call.getSourceNode(), state);
			// ==================================================

			NativeFunctions.expectParameters(nativeobject, call, c, 1, 1);
			Value thisval = state.readInternalValue(state.readThisObjects());
			Value propval = Conversion.toString(NativeFunctions.readParameter(call, 0), c);

			// ##################################################
			dependency.join(thisval.getDependency());
			dependency.join(propval.getDependency());
			// ##################################################

			// ==================================================
			node.addParent(thisval);
			node.addParent(propval);
			// ==================================================

			Set<ObjectLabel> thisobj = state.readThisObjects();
			if (propval.isMaybeAnyStr())
				return Value.makeAnyBool(dependency, node.getReference()); // TODO: could return
														// 'false' if thisobj
														// has no properties at
														// all
			else if (propval.isMaybeSingleStr()) {
				String propname = propval.getStr();
				Value val = state.readPropertyDirect(thisobj, propname);
				Value res = Value.makeBottom(dependency, node.getReference());
				if (val.isMaybeAbsent() || val.isNoValue())
					res = res.joinBool(false);
				if (val.isMaybeValue())
					res = res.joinBool(true);
				return res;
			} else
				return Value.makeBottom(dependency, node.getReference());
		}

		case OBJECT_ISPROTOTYPEOF: { // 15.2.4.6
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################

			// ==================================================
			DependencyExpressionNode node = DependencyNode.link(Label.CALL, call.getSourceNode(), state);
			// ==================================================

			NativeFunctions.expectParameters(nativeobject, call, c, 1, 1);
			Value thisval = state.readInternalValue(state.readThisObjects());
			Value prottyp = NativeFunctions.readParameter(call, 0);

			// ##################################################
			dependency.join(thisval.getDependency());
			dependency.join(prottyp.getDependency());
			// ##################################################

			// ==================================================
			node.addParent(thisval);
			node.addParent(prottyp);
			// ==================================================

			return Value.makeAnyBool(dependency, node.getReference()); // TODO:
																						// improve
																						// precision
																						// for
			// OBJECT_ISPROTOTYPEOF
		}

		case OBJECT_PROPERTYISENUMERABLE: { // 15.2.4.7
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################

			// ==================================================
			DependencyExpressionNode node = DependencyNode.link(Label.CALL, call.getSourceNode(), state);
			// ==================================================

			NativeFunctions.expectParameters(nativeobject, call, c, 1, 1);
			Value thisval = state.readInternalValue(state.readThisObjects());
			Value propval = Conversion.toString(NativeFunctions.readParameter(call, 0), c);

			// ##################################################
			dependency.join(thisval.getDependency());
			dependency.join(propval.getDependency());
			// ##################################################

			// ==================================================
			node.addParent(thisval);
			node.addParent(propval);
			// ==================================================

			Set<ObjectLabel> thisobj = state.readThisObjects();
			if (propval.isMaybeAnyStr())
				return Value.makeAnyBool(dependency, node.getReference()); // TODO: could return
														// 'false' if thisobj
														// has no properties at
														// all
			else if (propval.isMaybeSingleStr()) {
				String propname = propval.getStr();
				Value val = state.readPropertyDirect(thisobj, propname);
				Value res = Value.makeBottom(dependency, node.getReference());
				if (val.isMaybeAbsent() || val.isMaybeDontEnum() || val.isNoValue())
					res = res.joinBool(false);
				if (val.isMaybeValue() && val.isMaybeNotDontEnum())
					res = res.joinBool(true);
				return res;
			} else
				return Value.makeBottom(dependency, node.getReference());
		}

		default:
			return null;
		}
	}
}
