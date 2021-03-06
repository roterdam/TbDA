package dk.brics.tajs.analysis.dom.core;

import dk.brics.tajs.analysis.*;
import dk.brics.tajs.analysis.dom.*;
import dk.brics.tajs.analysis.dom.style.ClientBoundingRect;
import dk.brics.tajs.flowgraph.Node;
import dk.brics.tajs.flowgraph.ObjectLabel;
import dk.brics.tajs.lattice.Value;

import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMFunction;
import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMProperty;
import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMInternalPrototype;
import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMSpecialProperty;

import dk.brics.tajs.dependency.Dependency;
import dk.brics.tajs.dependency.graph.DependencyGraphReference;

/**
 * The Element interface represents an element in an HTML or XML document.
 */
public class DOMElement {

	public static ObjectLabel CONSTRUCTOR;
	public static ObjectLabel PROTOTYPE;
	public static ObjectLabel INSTANCES;

	public static void build(State s) {
		CONSTRUCTOR = new ObjectLabel(DOMObjects.ELEMENT_CONSTRUCTOR, ObjectLabel.Kind.FUNCTION);
		PROTOTYPE = new ObjectLabel(DOMObjects.ELEMENT_PROTOTYPE, ObjectLabel.Kind.OBJECT);
		INSTANCES = new ObjectLabel(DOMObjects.ELEMENT_INSTANCES, ObjectLabel.Kind.OBJECT);

		// Constructor Object
		s.newObject(CONSTRUCTOR);
		createDOMSpecialProperty(s, CONSTRUCTOR, "length", Value.makeNum(0, new Dependency(), new DependencyGraphReference()).setAttributes(true, true, true));
		createDOMSpecialProperty(s, CONSTRUCTOR, "prototype",
				Value.makeObject(PROTOTYPE, new Dependency(), new DependencyGraphReference()).setAttributes(true, true, true));
		createDOMInternalPrototype(s, CONSTRUCTOR, Value.makeObject(InitialStateBuilder.OBJECT_PROTOTYPE, new Dependency(), new DependencyGraphReference()));
		createDOMProperty(s, DOMWindow.WINDOW, "Element", Value.makeObject(CONSTRUCTOR, new Dependency(), new DependencyGraphReference()));

		// Prototype
		s.newObject(PROTOTYPE);
		createDOMInternalPrototype(s, PROTOTYPE, Value.makeObject(DOMNode.PROTOTYPE, new Dependency(), new DependencyGraphReference()));

		// Multiplied object
		s.newObject(INSTANCES);
		createDOMInternalPrototype(s, INSTANCES, Value.makeObject(PROTOTYPE, new Dependency(), new DependencyGraphReference()));

		/**
		 * Properties.
		 */
		// DOM Level 1
		createDOMProperty(s, INSTANCES, "tagName", Value.makeAnyStr(new Dependency(), new DependencyGraphReference()), DOMSpec.LEVEL_1);

		s.multiplyObject(INSTANCES);
		INSTANCES = INSTANCES.makeSingleton().makeSummary();

		/**
		 * Functions.
		 */
		// Unknown
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_GET_BOUNDING_CLIENT_RECT, "getBoundingClientRect", 0, DOMSpec.UNKNOWN);

		// DOM Level 1
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_GET_ATTRIBUTE, "getAttribute", 1, DOMSpec.LEVEL_1);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_SET_ATTRIBUTE, "setAttribute", 2, DOMSpec.LEVEL_1);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_REMOVE_ATTRIBUTE, "removeAttribute", 1, DOMSpec.LEVEL_1);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_GET_ATTRIBUTE_NODE, "getAttributeNode", 1, DOMSpec.LEVEL_1);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_SET_ATTRIBUTE_NODE, "setAttributeNode", 2, DOMSpec.LEVEL_1);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_REMOVE_ATTRIBUTE_NODE, "removeAttributeNode", 1, DOMSpec.LEVEL_1);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_GET_ELEMENTS_BY_TAGNAME, "getElementsByTagName", 1, DOMSpec.LEVEL_1);

		// DOM Level 2
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_GET_ATTRIBUTE_NS, "getAttributeNS", 2, DOMSpec.LEVEL_2);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_SET_ATTRIBUTE_NS, "setAttributeNS", 3, DOMSpec.LEVEL_2);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_REMOVE_ATTRIBUTE_NS, "removeAttributeNS", 2, DOMSpec.LEVEL_2);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_GET_ATTRIBUTE_NODE_NS, "getAttributeNodeNS", 2, DOMSpec.LEVEL_2);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_SET_ATTRIBUTE_NODE_NS, "setAttributeNodeNS", 3, DOMSpec.LEVEL_2);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_GET_ELEMENTS_BY_TAGNAME_NS, "getElementsByTagNameNS", 2, DOMSpec.LEVEL_2);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_HAS_ATTRIBUTE, "hasAttribute", 1, DOMSpec.LEVEL_2);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_HAS_ATTRIBUTE_NS, "hasAttributeNS", 2, DOMSpec.LEVEL_2);

		// DOM Level 3
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_SET_ID_ATTRIBUTE, "setIdAttribute", 2, DOMSpec.LEVEL_3);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_SET_ID_ATTRIBUTE_NS, "setIdAttributeNS", 3, DOMSpec.LEVEL_3);
		createDOMFunction(s, PROTOTYPE, DOMObjects.ELEMENT_SET_ID_ATTRIBUTE_NODE, "setIdAttributeNode", 2, DOMSpec.LEVEL_3);

		// DOM Level 2
		createDOMProperty(s, DOMAttr.INSTANCES, "ownerElement", Value.makeObject(DOMElement.INSTANCES, new Dependency(), new DependencyGraphReference())
				.setReadOnly(), DOMSpec.LEVEL_2);
	}

	/**
	 * Transfer Functions.
	 */
	public static Value evaluate(DOMObjects nativeObject, FunctionCalls.CallInfo<? extends Node> call, State s, Solver.SolverInterface c) {
		switch (nativeObject) {
		case ELEMENT_GET_ATTRIBUTE: {
			NativeFunctions.expectParameters(nativeObject, call, c, 1, 1);
			/* Value name = */Conversion.toString(call.getArg(0), c);
			return Value.makeAnyStr(new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_SET_ATTRIBUTE: {
			NativeFunctions.expectParameters(nativeObject, call, c, 2, 2);
			/* Value name = */Conversion.toString(call.getArg(0), c);
			/* Value value = */Conversion.toString(call.getArg(1), c);
			return Value.makeUndef(new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_REMOVE_ATTRIBUTE: {
			NativeFunctions.expectParameters(nativeObject, call, c, 1, 1);
			/* Value name = */Conversion.toString(call.getArg(0), c);
			return Value.makeUndef(new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_GET_ATTRIBUTE_NS: {
			NativeFunctions.expectParameters(nativeObject, call, c, 2, 2);
			/* Value namespace = */Conversion.toString(call.getArg(0), c);
			/* Value name = */Conversion.toString(call.getArg(1), c);
			return Value.makeAnyStr(new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_GET_ATTRIBUTE_NODE: {
			NativeFunctions.expectParameters(nativeObject, call, c, 1, 1);
			/* Value name = */Conversion.toString(call.getArg(0), c);
			return Value.makeObject(DOMAttr.INSTANCES, new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_GET_ATTRIBUTE_NODE_NS: {
			NativeFunctions.expectParameters(nativeObject, call, c, 2, 2);
			/* Value namespace = */Conversion.toString(call.getArg(0), c);
			/* Value name = */Conversion.toString(call.getArg(1), c);
			return Value.makeObject(DOMAttr.INSTANCES, new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_GET_BOUNDING_CLIENT_RECT: {
			NativeFunctions.expectParameters(nativeObject, call, c, 0, 0);
			return Value.makeObject(ClientBoundingRect.INSTANCES, new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_GET_ELEMENTS_BY_TAGNAME: {
			// TODO: needs precision, but cannot do like
			// document.getElementsByTagName() bc. State is for everything
			NativeFunctions.expectParameters(nativeObject, call, c, 1, 1);
			/* Value name = */Conversion.toString(call.getArg(0), c);
			return Value.makeObject(DOMNodeList.INSTANCES, new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_GET_ELEMENTS_BY_TAGNAME_NS: {
			NativeFunctions.expectParameters(nativeObject, call, c, 2, 2);
			/* Value namespace = */Conversion.toString(call.getArg(0), c);
			/* Value name = */Conversion.toString(call.getArg(1), c);
			return Value.makeObject(DOMNodeList.INSTANCES, new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_HAS_ATTRIBUTE: {
			NativeFunctions.expectParameters(nativeObject, call, c, 1, 1);
			/* Value name = */Conversion.toString(call.getArg(0), c);
			return Value.makeAnyBool(new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_HAS_ATTRIBUTE_NS: {
			NativeFunctions.expectParameters(nativeObject, call, c, 2, 2);
			/* Value namespace = */Conversion.toString(call.getArg(0), c);
			/* Value name = */Conversion.toString(call.getArg(1), c);
			return Value.makeAnyBool(new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_REMOVE_ATTRIBUTE_NS: {
			NativeFunctions.expectParameters(nativeObject, call, c, 2, 2);
			/* Value namespaceURI = */Conversion.toString(call.getArg(0), c);
			/* Value localName = */Conversion.toString(call.getArg(1), c);
			return Value.makeUndef(new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_REMOVE_ATTRIBUTE_NODE: {
			NativeFunctions.expectParameters(nativeObject, call, c, 1, 1);
			Value oldAttr = DOMConversion.toAttr(call.getArg(0), c);
			return oldAttr;
		}
		case ELEMENT_SET_ATTRIBUTE_NS: {
			NativeFunctions.expectParameters(nativeObject, call, c, 3, 3);
			/* Value namespace = */Conversion.toString(call.getArg(0), c).joinNull();
			/* Value name = */Conversion.toString(call.getArg(1), c);
			/* Value value = */Conversion.toString(call.getArg(2), c);
			return Value.makeUndef(new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_SET_ATTRIBUTE_NODE: {
			NativeFunctions.expectParameters(nativeObject, call, c, 1, 1);
			/* Value newAttr = */DOMConversion.toAttr(call.getArg(0), c);
			return Value.makeObject(DOMAttr.INSTANCES, new Dependency(), new DependencyGraphReference()).joinNull();
		}
		case ELEMENT_SET_ATTRIBUTE_NODE_NS: {
			NativeFunctions.expectParameters(nativeObject, call, c, 1, 1);
			/* Value newAttr = */DOMConversion.toAttr(call.getArg(0), c);
			return Value.makeObject(DOMAttr.INSTANCES, new Dependency(), new DependencyGraphReference()).joinNull();
		}
		case ELEMENT_SET_ID_ATTRIBUTE: {
			NativeFunctions.expectParameters(nativeObject, call, c, 2, 2);
			/* Value name = */Conversion.toString(call.getArg(0), c);
			/* Value isId = */Conversion.toBoolean(call.getArg(1));
			return Value.makeUndef(new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_SET_ID_ATTRIBUTE_NS: {
			NativeFunctions.expectParameters(nativeObject, call, c, 3, 3);
			/* Value namespaceURI = */Conversion.toString(call.getArg(0), c);
			/* Value localName = */Conversion.toString(call.getArg(1), c);
			/* Value isId = */Conversion.toBoolean(call.getArg(2));
			return Value.makeUndef(new Dependency(), new DependencyGraphReference());
		}
		case ELEMENT_SET_ID_ATTRIBUTE_NODE: {
			NativeFunctions.expectParameters(nativeObject, call, c, 2, 2);
			/* Value idAttr = */DOMConversion.toAttr(call.getArg(0), c);
			/* Value isId = */Conversion.toBoolean(call.getArg(1));
			return Value.makeUndef(new Dependency(), new DependencyGraphReference());
		}
		default: {
			throw new RuntimeException("Unknown Native Object: " + nativeObject);
		}
		}
	}
}
