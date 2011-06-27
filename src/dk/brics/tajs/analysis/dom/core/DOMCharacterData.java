package dk.brics.tajs.analysis.dom.core;

import dk.brics.tajs.analysis.Conversion;
import dk.brics.tajs.analysis.FunctionCalls;
import dk.brics.tajs.analysis.NativeFunctions;
import dk.brics.tajs.analysis.Solver;
import dk.brics.tajs.analysis.State;
import dk.brics.tajs.analysis.dom.DOMObjects;
import dk.brics.tajs.analysis.dom.DOMSpec;
import dk.brics.tajs.analysis.dom.DOMWindow;
import dk.brics.tajs.dependency.Dependency;
import dk.brics.tajs.dependency.graph.DependencyGraphReference;
import dk.brics.tajs.flowgraph.ObjectLabel;
import dk.brics.tajs.lattice.Value;

import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMFunction;
import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMProperty;
import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMInternalPrototype;


/**
 * The CharacterData interface extends Node with a set of attributes and methods
 * for accessing character data in the DOM. For clarity this set is defined here
 * rather than on each object that uses these attributes and methods. No DOM
 * objects correspond directly to CharacterData, though Text and others do
 * inherit the interface from it. All offsets in this interface start from 0.
 */
public class DOMCharacterData {

	public static ObjectLabel CHARACTER_DATA = new ObjectLabel(DOMObjects.CHARACTERDATA, ObjectLabel.Kind.OBJECT);
	public static ObjectLabel CHARACTER_DATA_PROTOTYPE = new ObjectLabel(DOMObjects.CHARACTERDATA_PROTOTYPE, ObjectLabel.Kind.OBJECT);

	public static void build(State s) {
		// Prototype object.
		s.newObject(CHARACTER_DATA_PROTOTYPE);
		createDOMInternalPrototype(s, CHARACTER_DATA_PROTOTYPE, Value.makeObject(DOMNode.NODE_PROTOTYPE, new Dependency(), new DependencyGraphReference()));

		// Multiplied object.
		s.newObject(CHARACTER_DATA);
		createDOMInternalPrototype(s, CHARACTER_DATA, Value.makeObject(CHARACTER_DATA_PROTOTYPE, new Dependency(), new DependencyGraphReference()));
		createDOMProperty(s, DOMWindow.WINDOW, "CharacterData", Value.makeObject(CHARACTER_DATA, new Dependency(), new DependencyGraphReference()));

		/*
		 * Properties.
		 */
		// DOM Level 1
		createDOMProperty(s, CHARACTER_DATA, "data", Value.makeAnyStr(new Dependency(), new DependencyGraphReference()), DOMSpec.LEVEL_1);
		createDOMProperty(s, CHARACTER_DATA, "length", Value.makeAnyNumUInt(new Dependency(), new DependencyGraphReference()), DOMSpec.LEVEL_1);

		s.multiplyObject(CHARACTER_DATA);
		CHARACTER_DATA = CHARACTER_DATA.makeSingleton().makeSummary();

		/*
		 * Functions.
		 */
		// DOM Level 1
		createDOMFunction(s, CHARACTER_DATA_PROTOTYPE, DOMObjects.CHARACTERDATA_SUBSTRINGDATA, "substringData", 2, DOMSpec.LEVEL_1);
		createDOMFunction(s, CHARACTER_DATA_PROTOTYPE, DOMObjects.CHARACTERDATA_APPENDDATA, "appendData", 1, DOMSpec.LEVEL_1);
		createDOMFunction(s, CHARACTER_DATA_PROTOTYPE, DOMObjects.CHARACTERDATA_INSERTDATA, "insertData", 2, DOMSpec.LEVEL_1);
		createDOMFunction(s, CHARACTER_DATA_PROTOTYPE, DOMObjects.CHARACTERDATA_DELETEDATA, "deleteData", 2, DOMSpec.LEVEL_1);
		createDOMFunction(s, CHARACTER_DATA_PROTOTYPE, DOMObjects.CHARACTERDATA_REPLACEDATA, "replaceData", 3, DOMSpec.LEVEL_1);
	}

	public static Value evaluate(DOMObjects nativeobject, FunctionCalls.CallInfo call, State s, Solver.SolverInterface c) {
		switch (nativeobject) {
		case CHARACTERDATA_APPENDDATA: {
			NativeFunctions.expectParameters(nativeobject, call, c, 1, 1);
			Value arg = Conversion.toString(call.getArg(0), c);
			return Value.makeUndef(new Dependency(), new DependencyGraphReference());
		}
		case CHARACTERDATA_DELETEDATA: {
			NativeFunctions.expectParameters(nativeobject, call, c, 2, 2);
			Value offset = Conversion.toNumber(call.getArg(0), c);
			Value count = Conversion.toNumber(call.getArg(1), c);
			return Value.makeUndef(new Dependency(), new DependencyGraphReference());
		}
		case CHARACTERDATA_REPLACEDATA: {
			NativeFunctions.expectParameters(nativeobject, call, c, 3, 3);
			Value offset = Conversion.toNumber(call.getArg(0), c);
			Value count = Conversion.toNumber(call.getArg(1), c);
			Value arg = Conversion.toString(call.getArg(2), c);
			return Value.makeUndef(new Dependency(), new DependencyGraphReference());
		}
		case CHARACTERDATA_SUBSTRINGDATA: {
			NativeFunctions.expectParameters(nativeobject, call, c, 2, 2);
			Value offset = Conversion.toNumber(call.getArg(0), c);
			Value count = Conversion.toNumber(call.getArg(1), c);
			return Value.makeAnyStr(new Dependency(), new DependencyGraphReference());
		}
		case CHARACTERDATA_INSERTDATA: {
			NativeFunctions.expectParameters(nativeobject, call, c, 2, 2);
			Value offset = Conversion.toNumber(call.getArg(0), c);
			Value arg = Conversion.toString(call.getArg(1), c);
			return Value.makeUndef(new Dependency(), new DependencyGraphReference());
		}
		default:
			throw new RuntimeException("Unsupported Native Object: " + nativeobject);
		}
	}
}
