package dk.brics.tajs.analysis.dom.html;

import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMFunction;
import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMProperty;
import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMInternalPrototype;
import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMSpecialProperty;

import dk.brics.tajs.analysis.Conversion;
import dk.brics.tajs.analysis.FunctionCalls;
import dk.brics.tajs.analysis.NativeFunctions;
import dk.brics.tajs.analysis.Solver;
import dk.brics.tajs.analysis.State;
import dk.brics.tajs.analysis.dom.DOMFunctions;
import dk.brics.tajs.analysis.dom.DOMObjects;
import dk.brics.tajs.analysis.dom.DOMSpec;
import dk.brics.tajs.analysis.dom.DOMWindow;
import dk.brics.tajs.analysis.dom.core.DOMElement;
import dk.brics.tajs.analysis.dom.style.CSSStyleDeclaration;
import dk.brics.tajs.flowgraph.Node;
import dk.brics.tajs.flowgraph.ObjectLabel;
import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.util.Collections;

import dk.brics.tajs.dependency.Dependency;
import dk.brics.tajs.dependency.graph.DependencyGraphReference;

public class HTMLElement {

	public static ObjectLabel ELEMENT;
	public static ObjectLabel ELEMENT_PROTOTYPE;
	public static ObjectLabel ELEMENT_ATTRIBUTES;

	public static void build(State s) {
		ELEMENT = new ObjectLabel(DOMObjects.HTMLELEMENT, ObjectLabel.Kind.OBJECT);
		ELEMENT_PROTOTYPE = new ObjectLabel(DOMObjects.HTMLELEMENT_PROTOTYPE, ObjectLabel.Kind.OBJECT);
		ELEMENT_ATTRIBUTES = new ObjectLabel(DOMObjects.HTMLELEMENT_ATTRIBUTES, ObjectLabel.Kind.OBJECT);

		createDOMSpecialProperty(s, ELEMENT, "length", Value.makeNum(0, new Dependency(), new DependencyGraphReference()).setAttributes(true, true, true)); // FIXME!
		createDOMSpecialProperty(s, ELEMENT, "prototype",
				Value.makeObject(ELEMENT_PROTOTYPE, new Dependency(), new DependencyGraphReference()).setAttributes(true, true, true));

		// Prototype Object
		s.newObject(ELEMENT_PROTOTYPE);
		createDOMInternalPrototype(s, ELEMENT_PROTOTYPE, Value.makeObject(DOMElement.PROTOTYPE, new Dependency(), new DependencyGraphReference()));

		// Multiplied Object
		s.newObject(ELEMENT);
		createDOMInternalPrototype(s, ELEMENT, Value.makeObject(ELEMENT_PROTOTYPE, new Dependency(), new DependencyGraphReference()));
		createDOMProperty(s, DOMWindow.WINDOW, "HTMLElement", Value.makeObject(ELEMENT, new Dependency(), new DependencyGraphReference()));

		/*
		 * Properties.
		 */
		// DOM Level 1
		// Note: id attribute not set here.
		createDOMProperty(s, ELEMENT_PROTOTYPE, "title", Value.makeAnyStr(new Dependency(), new DependencyGraphReference()), DOMSpec.LEVEL_1);
		createDOMProperty(s, ELEMENT_PROTOTYPE, "lang", Value.makeAnyStr(new Dependency(), new DependencyGraphReference()), DOMSpec.LEVEL_1);
		createDOMProperty(s, ELEMENT_PROTOTYPE, "dir", Value.makeAnyStr(new Dependency(), new DependencyGraphReference()), DOMSpec.LEVEL_1);
		createDOMProperty(s, ELEMENT_PROTOTYPE, "className", Value.makeAnyStr(new Dependency(), new DependencyGraphReference()), DOMSpec.LEVEL_1);

		// DOM LEVEL 0
		createDOMProperty(s, ELEMENT_PROTOTYPE, "clientHeight", Value.makeAnyNumUInt(new Dependency(), new DependencyGraphReference()), DOMSpec.LEVEL_0);
		createDOMProperty(s, ELEMENT_PROTOTYPE, "clientWidth", Value.makeAnyNumUInt(new Dependency(), new DependencyGraphReference()), DOMSpec.LEVEL_0);

		// MSIE
		createDOMProperty(s, ELEMENT_PROTOTYPE, "offsetLeft", Value.makeAnyNumUInt(new Dependency(), new DependencyGraphReference()), DOMSpec.MSIE);
		createDOMProperty(s, ELEMENT_PROTOTYPE, "offsetTop", Value.makeAnyNumUInt(new Dependency(), new DependencyGraphReference()), DOMSpec.MSIE);
		createDOMProperty(s, ELEMENT_PROTOTYPE, "offsetParent", Value.makeAnyNumUInt(new Dependency(), new DependencyGraphReference()), DOMSpec.MSIE);
		createDOMProperty(s, ELEMENT_PROTOTYPE, "offsetHeight", Value.makeAnyNumUInt(new Dependency(), new DependencyGraphReference()), DOMSpec.MSIE);
		createDOMProperty(s, ELEMENT_PROTOTYPE, "offsetWidth", Value.makeAnyNumUInt(new Dependency(), new DependencyGraphReference()), DOMSpec.MSIE);

		// DOM LEVEL 0
		s.newObject(ELEMENT_ATTRIBUTES);
		s.writeUnknownProperty(Collections.newSingleton(ELEMENT_ATTRIBUTES), Value.makeAnyStr(new Dependency(), new DependencyGraphReference()));
		s.multiplyObject(ELEMENT_ATTRIBUTES);
		ELEMENT_ATTRIBUTES = ELEMENT_ATTRIBUTES.makeSingleton().makeSummary();
		createDOMProperty(s, ELEMENT_PROTOTYPE, "attributes", Value.makeObject(ELEMENT_ATTRIBUTES, new Dependency(), new DependencyGraphReference()),
				DOMSpec.LEVEL_0);

		// Style
		createDOMProperty(s, ELEMENT_PROTOTYPE, "style",
				Value.makeObject(CSSStyleDeclaration.STYLEDECLARATION, new Dependency(), new DependencyGraphReference()), DOMSpec.LEVEL_1);

		s.multiplyObject(ELEMENT);
		ELEMENT = ELEMENT.makeSingleton().makeSummary();

		/*
		 * Functions.
		 */
		createDOMFunction(s, ELEMENT_PROTOTYPE, DOMObjects.HTMLELEMENT_GET_ELEMENTS_BY_CLASS_NAME, "getElementsByClassName", 1, DOMSpec.LEVEL_0);
	}

	/**
	 * Transfer Functions.
	 */
	public static Value evaluate(DOMObjects nativeObject, FunctionCalls.CallInfo<? extends Node> call, State s, Solver.SolverInterface c) {
		switch (nativeObject) {
		case HTMLELEMENT_GET_ELEMENTS_BY_CLASS_NAME: {
			NativeFunctions.expectParameters(nativeObject, call, c, 1, 1);
			/* Value className = */Conversion.toString(call.getArg(0), c);
			return DOMFunctions.makeAnyHTMLNodeList(s);
		}
		default: {
			throw new RuntimeException("Unsupported Native Object: " + nativeObject);
		}
		}
	}

}
