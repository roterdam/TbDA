package dk.brics.tajs.analysis.dom;

import java.util.Set;

import dk.brics.tajs.analysis.Solver;
import dk.brics.tajs.analysis.State;
import dk.brics.tajs.dependency.Dependency;
import dk.brics.tajs.dependency.graph.DependencyGraphReference;
import dk.brics.tajs.flowgraph.NativeObject;
import dk.brics.tajs.flowgraph.ObjectLabel;
import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.solver.Message;
import dk.brics.tajs.util.Collections;

public class DOMConversion {

	/**
	 * Converts the given value to a DOMNode value.
	 */
	public static Value toNode(Value v, Solver.SolverInterface c) {
		return toNativeObject(DOMObjects.NODE, v, true, c);
	}

	/**
	 * Converts the given value to a DOMAttr value.
	 */
	public static Value toAttr(Value v, Solver.SolverInterface c) {
		return toNativeObject(DOMObjects.ATTR, v, true, c);
	}

	/**
	 * Converts the given value to a HTMLElement value.
	 */
	public static Value toHTMLElement(Value v, Solver.SolverInterface c) {
		return toNativeObject(DOMObjects.HTMLELEMENT, v, true, c);
	}

	/**
	 * Converts the given value to an EventTarget value.
	 */
	public static Value toEventTarget(Value v, Solver.SolverInterface c) {
		return toNativeObject(DOMObjects.NODE, v, true, c);
	}

	/**
	 * Converts the given value to an EventHandler value.
	 */
	public static Value toEventHandler(State state, Value value, Solver.SolverInterface c) {
		final String message = "TypeError, non-function event handler.";
		Set<ObjectLabel> result = Collections.newSet();

		boolean maybeFunction = false;
		boolean maybeNonFunction = value.isMaybePrimitive();

		for (ObjectLabel objectLabel : value.getObjectLabels()) {
			if (objectLabel.getKind() == ObjectLabel.Kind.FUNCTION) {
				maybeFunction = true;
				result.add(objectLabel);
			} else {
				maybeNonFunction = true;
			}
		}

		Message.Status status = Message.Status.NONE;
		if (maybeNonFunction) {
			if (maybeFunction) {
				status = Message.Status.MAYBE;
			} else {
				status = Message.Status.CERTAIN;
			}
		}

		if (status != Message.Status.NONE) {
			c.addMessage(status, Message.Severity.HIGH, message);
		}

		return Value.makeObject(result, value.getDependency(), new DependencyGraphReference());
	}

	/**
	 * Converts the given value to the given NativeObject (optionally following
	 * the prototype chains). </p>
	 *
	 * @param nativeObject    target NativeObject
	 * @param value           Value to convert
	 * @param prototype       use the prototype chain?
	 * @param solverInterface
	 */
	public static Value toNativeObject(NativeObject nativeObject, Value value, boolean prototype, Solver.SolverInterface solverInterface) {

		// ##################################################
		Dependency dependency = new Dependency() ;
		dependency.join(value.getDependency());

		DependencyGraphReference reference = new DependencyGraphReference();
		reference.join(value.getDependencyGraphReference());
		// ##################################################

		State state = solverInterface.getCurrentState();
		boolean good = false;
		boolean bad = false;
		Set<ObjectLabel> matches = Collections.newSet();

		if (prototype) {
			// Make lookup using prototype chains
			Set<ObjectLabel> objectLabels = Collections.newSet(value.getObjectLabels());
			Set<ObjectLabel> visited = Collections.newSet();

			while (!objectLabels.isEmpty()) {
				Set<ObjectLabel> temp = Collections.newSet();
				for (ObjectLabel objectLabel : objectLabels) {
					if (!visited.contains(objectLabel)) {
						visited.add(objectLabel);
						Value prototypeValue = state.readInternalPrototype(objectLabel);

						// ##################################################
						dependency.join(prototypeValue.getDependency());
						reference.join(prototypeValue.getDependencyGraphReference());
						// ##################################################

						if (objectLabel.getNativeObjectID().toString().equals(nativeObject.toString()) || objectLabel.getNativeObjectID().toString().equals(nativeObject.toString() + ".prototype")) {
							good = true;
							matches.add(objectLabel);
						} else if (prototypeValue.getObjectLabels().isEmpty()) {
							bad = true;
						} else {
							temp.addAll(prototypeValue.getObjectLabels());
						}
					}
				}
				objectLabels = temp;
			}

		} else {
			// Make lookup ignoring prototype chains

			for (ObjectLabel objectLabel : value.getObjectLabels()) {
				if (objectLabel.getNativeObjectID().toString().equals(nativeObject.toString()) || objectLabel.getNativeObjectID().toString().equals(nativeObject.toString() + ".prototype")) {
					good = true;
					matches.add(objectLabel);
				} else {
					bad = true;
				}
			}

		}

		Message.Status status;
		if (good && bad) {
			status = Message.Status.MAYBE;
		} else if (!good && bad) {
			status = Message.Status.CERTAIN;
		} else if (good && !bad) {
			status = Message.Status.NONE;
		} else if (!good && !bad) { // equivalent to Value of Undef / a null argument
			// Considered a certain type error.
			status = Message.Status.CERTAIN;
		} else {
			throw new RuntimeException("toNativeObject: fell through cases - should not happen.");
		}

		String message = "TypeError, argument is not of expected type.";
		switch (status) {
		case NONE: {
			break;
		}
		default: {
			solverInterface.addMessage(solverInterface.getCurrentNode(), status, Message.Severity.HIGH, message);
		}
		}

		return Value.makeObject(matches, dependency, reference);
	}

}
