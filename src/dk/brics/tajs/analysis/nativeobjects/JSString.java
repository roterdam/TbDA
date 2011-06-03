package dk.brics.tajs.analysis.nativeobjects;

import dk.brics.tajs.analysis.Conversion;
import dk.brics.tajs.analysis.InitialStateBuilder;
import dk.brics.tajs.analysis.NativeFunctions;
import dk.brics.tajs.analysis.Solver;
import dk.brics.tajs.analysis.State;
import dk.brics.tajs.analysis.FunctionCalls.CallInfo;
import dk.brics.tajs.dependency.Dependency;
import dk.brics.tajs.dependency.DependencyObject;
import dk.brics.tajs.flowgraph.ObjectLabel;
import dk.brics.tajs.flowgraph.ObjectLabel.Kind;
import dk.brics.tajs.lattice.Value;

/**
 * 15.5 native String functions.
 */
public class JSString {

	private JSString() {}

	/**
	 * Evaluates the given native function.
	 */
	public static Value evaluate(ECMAScriptObjects nativeobject, CallInfo call, State state, Solver.SolverInterface c) {
		if (nativeobject != ECMAScriptObjects.STRING)
			if (NativeFunctions.throwTypeErrorIfConstructor(call, state, c))
				return Value.makeBottom(new Dependency());

		switch (nativeobject) {

		case STRING: { // 15.5.1/2
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 0, 1);
			Value s;
			if (call.isUnknownNumberOfArgs())
				s = Conversion.toString(NativeFunctions.readParameter(call, 0), c).joinStr("");
			else
				s = call.getNumberOfArgs() >= 1 ? Conversion.toString(NativeFunctions.readParameter(call, 0), c) : Value.makeStr("", dependency);

			// ##################################################
			dependency.join(s.getDependency());
			// ##################################################
				
				if (call.isConstructorCall()) { // 15.5.2
				ObjectLabel objlabel = new ObjectLabel(call.getSourceNode(), Kind.STRING);
				state.newObject(objlabel);
				state.writeInternalValue(objlabel, s);
				state.writeInternalPrototype(objlabel, Value.makeObject(InitialStateBuilder.STRING_PROTOTYPE, dependency));
				Value len = s.isMaybeSingleStr() ? Value.makeNum(s.getStr().length(), s.getDependency()) : Value.makeAnyNumUInt(dependency);
				state.writeSpecialProperty(objlabel, "length", len.setAttributes(true, true, true));
				return Value.makeObject(objlabel, dependency);
			} else // 15.5.1
				return s;
		}

		case STRING_FROMCHARCODE: { // 15.5.3.2
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			if (call.isUnknownNumberOfArgs())
				return Value.makeAnyStr(dependency);
			StringBuilder b = new StringBuilder(call.getNumberOfArgs());
			for (int i = 0; i < call.getNumberOfArgs(); i++) {
				Value v = Conversion.toNumber(call.getArg(i), c);

				// ##################################################
				dependency.join(v.getDependency());
				// ##################################################

				if (v.isMaybeSingleNum()) {
					long codepoint = Conversion.toUInt16(v.getNum());
					b.append((char)codepoint);
				} else
					return Value.makeAnyStr(dependency);
			}
			return Value.makeStr(b.toString(), dependency);
		}
					
		case STRING_TOSTRING: // 15.5.4.2
		case STRING_VALUEOF: { // 15.5.4.3
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 0, 0);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			// ##################################################
			
			if (NativeFunctions.throwTypeErrorIfWrongKindOfThis(nativeobject, call, state, c, Kind.STRING))
				return Value.makeBottom(dependency);
			return state.readInternalValue(state.readThisObjects());
		}

		case STRING_CHARAT: // 15.5.4.4
		case STRING_CHARCODEAT: { // 15.5.4.5
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 1, 1);
			Value receiver = Conversion.toString(Value.makeObject(state.readThisObjects(), dependency), c);
			Value str = Conversion.toString(receiver, c);
			Value pos = Conversion.toInteger(call.getArg(0), c);
			
			// ##################################################
			dependency.join(receiver.getDependency());
			dependency.join(str.getDependency());
			dependency.join(pos.getDependency());
			// ##################################################
			
			if (str.isMaybeSingleStr() && pos.isMaybeSingleNum()) { // FIXME: may also be maybe non-single!
				String s = str.getStr();
				double p = pos.getNum();
				try {
					char ch = s.charAt((int)Math.round(p));
					if (nativeobject == ECMAScriptObjects.STRING_CHARAT)
						return Value.makeStr(new String(new char[]{ch}), dependency);
					else
						return Value.makeNum(ch, dependency);
				} catch (IndexOutOfBoundsException e) {
					if (nativeobject == ECMAScriptObjects.STRING_CHARAT)
						return Value.makeStr("", dependency);
					else
						return Value.makeNum(Double.NaN, dependency);
				}
			} else
				return Value.makeAnyStr(dependency);
		}
			
		case STRING_CONCAT: { // 15.5.4.6
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			// ##################################################

		
			// return Value.makeAnyStr(); // TODO: improve precision?
			if (call.isUnknownNumberOfArgs())
				return Value.makeAnyStr(dependency);
			String r = "";
			for (int i = 0; i < call.getNumberOfArgs(); i++) {
				Value argi = Conversion.toString(call.getArg(i),c);
				dependency.join(call.getArg(i).getDependency());
				
				if (argi.isMaybeAnyStr())
					return Value.makeAnyStr(dependency);
				if (argi.isNoValue()) 
					return Value.makeBottom(dependency);
				r = r + argi.getStr();
			}
			return Value.makeStr(r, dependency);
		}
					
		case STRING_INDEXOF: // 15.5.4.7
		case STRING_LASTINDEXOF: { // 15.5.4.8
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################

			NativeFunctions.expectParameters(nativeobject, call, c, 1, 2);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			// ##################################################
			
			if (call.getNumberOfArgs() == 1) {
				// ##################################################
				dependency.join(call.getArg(0).getDependency());
				// ##################################################
			}
			else if (call.getNumberOfArgs() == 2) {
				// ##################################################
				dependency.join(call.getArg(0).getDependency());
				dependency.join(call.getArg(1).getDependency());
				// ##################################################
			}
			
			return Value.makeAnyNumNotNaNInf(dependency); // TODO: improve precision?

			
//			Value vthis = Conversion.toString(Value.makeObject(call.getThis(state)),c);
//			if (vthis.isMaybeAnyStr())
//				return Value.makeAnyNumNotNaNInf();
//			String thisstr = vthis.getStr();
//			Value vSearchString;// = Conversion.toString(params <= 2 ? Value.makeUndef() : call.getArg(first_param));
//			Value vPosition;
//			if (call.isUnknownNumberOfArgs()) {
//				vSearchString = call.getUnknownArg().joinUndef();
//				vPosition = call.getUnknownArg().joinUndef();;
//			}
//			else if (call.getNumberOfArgs() == 0) {
//				vSearchString = Value.makeUndef();
//				vPosition = Value.makeUndef();
//			}
//			else if (call.getNumberOfArgs() == 1) {
//				vSearchString = call.getArg(0);
//				vPosition = Value.makeUndef();
//			}
//			else {
//				vSearchString = call.getArg(0);
//				vPosition = call.getArg(1);
//			}
//			vSearchString = Conversion.toString(vSearchString, c);
//			if (vSearchString.isMaybeAnyStr())
//				return Value.makeAnyNumNotNaNInf();
//			boolean isIndexOf = nativeobject.equals(NativeObjects.STRING_INDEXOF);
//			int defaultPosition = isIndexOf ? 0 : thisstr.length();
//			vPosition = (vPosition.isMaybeUndef() ? Value.makeNum(defaultPosition) : Value.makeBottom()).join(vPosition);
//			vPosition= Conversion.toInteger(vPosition,c);
//			if (vPosition.isMaybeFuzzyNum())
//				return Value.makeAnyNumNotNaNInf();
//			String searchString = vSearchString.getStr();
//			int position = vPosition.getNum().intValue();
//			int result = isIndexOf ? thisstr.indexOf(searchString, position) : thisstr.lastIndexOf(searchString, position);
//			return Value.makeNum(result);
		}
		
		case STRING_LOCALECOMPARE: { // 15.5.4.9
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 1, 1);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			dependency.join(call.getArg(0).getDependency());
			// ##################################################
			
			return Value.makeAnyNum(dependency); // TODO: improve precision?
		}
					
		case STRING_MATCH: { // 15.5.4.10 (see REGEXP_EXEC)
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 1, 1);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			dependency.join(call.getArg(0).getDependency());
			// ##################################################

			
			ObjectLabel objlabel = new ObjectLabel(call.getSourceNode(), Kind.ARRAY);
			state.newObject(objlabel);
			Value res = Value.makeObject(objlabel, dependency);
			state.writeInternalPrototype(objlabel, Value.makeObject(InitialStateBuilder.ARRAY_PROTOTYPE, dependency));
			state.writeUnknownArrayProperty(objlabel, Value.makeAnyStr(dependency).joinAbsent());
			state.writeSpecialProperty(objlabel, "length", Value.makeAnyNumUInt(dependency).setAttributes(true, true, false));
			state.writeProperty(objlabel, "index", Value.makeAnyNumUInt(dependency));
			state.writeProperty(objlabel, "input", Value.makeAnyStr(dependency)); // TODO: improve precision?
			return res.joinNull();
		}
					
		case STRING_REPLACE: { // 15.5.4.11
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 2, 2);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			dependency.join(call.getArg(0).getDependency());
			dependency.join(call.getArg(1).getDependency());
			// ##################################################
			
			// FIXME: if second param to 'replace' is a function, it may be called, even several times!
			return Value.makeAnyStr(dependency); // complicated regexp stuff
		}

		case STRING_SEARCH: { // 15.5.4.12
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 1, 1);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			dependency.join(call.getArg(0).getDependency());
			// ##################################################
			
			return Value.makeAnyNumNotNaNInf(dependency); // TODO: improve precision?
		}
		
		case STRING_SLICE: { // 15.5.4.13
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 1, 2);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			// ##################################################
			
			if (call.getNumberOfArgs() == 1) {
				// ##################################################
				dependency.join(call.getArg(0).getDependency());
				// ##################################################
			}
			else if (call.getNumberOfArgs() == 2) {
				// ##################################################
				dependency.join(call.getArg(0).getDependency());
				dependency.join(call.getArg(1).getDependency());
				// ##################################################
			}
			
			return Value.makeAnyStr(dependency); // TODO: improve precision?
//			NativeFunctions.expectParameters(nativeobject, call, c, 2, 2);
//			Value vthis = Conversion.toString(Value.makeObject(call.getThis(state)),c);
//			if (vthis.isMaybeAnyStr() || call.isUnknownNumberOfArgs())
//				return Value.makeAnyStr();
//			String strthis = vthis.getStr();
//			int sourcelength = strthis.length();
//			Value vstart = call.getArg(0);
//			Value vend = call.getArg(1);
//			if (vend.isMaybeUndef())
//				vend = vend.join(Value.makeNum(sourcelength));
//			vstart = Conversion.toInteger(vstart,c);
//			vend = Conversion.toInteger(vend,c);
//			if (vstart.isMaybeFuzzyNum() || vend.isMaybeFuzzyNum())
//				return Value.makeAnyStr();
//			int istart = vstart.getNum().intValue();
//			int iend = vend.getNum().intValue();
//			int result5 = istart < 0 ? Math.max(0, sourcelength + istart) : Math.min(sourcelength, istart);
//			int result6 = iend < 0   ? Math.max(0, sourcelength + iend) : Math.min(sourcelength, iend);
//			int result7 = Math.max(0, result6 - result5);
//			return Value.makeStr(strthis.substring(result5, result5 + result7));
		}

		case STRING_SPLIT: { // 15.5.4.14
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 1, 2);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			// ##################################################
			
			if (call.getNumberOfArgs() == 1) {
				// ##################################################
				dependency.join(call.getArg(0).getDependency());
				// ##################################################
			}
			else if (call.getNumberOfArgs() == 2) {
				// ##################################################
				dependency.join(call.getArg(0).getDependency());
				dependency.join(call.getArg(1).getDependency());
				// ##################################################
			}
			
			ObjectLabel aobj = new ObjectLabel(call.getSourceNode(), Kind.ARRAY);
			state.newObject(aobj);
			state.writeInternalPrototype(aobj, Value.makeObject(InitialStateBuilder.ARRAY_PROTOTYPE, dependency));
			state.writeUnknownArrayProperty(aobj, Value.makeAnyStr(dependency));
			state.writeSpecialProperty(aobj, "length", Value.makeAnyNumUInt(dependency).setAttributes(true, true, false));
			return Value.makeObject(aobj, dependency); // TODO: improve precision?		
		}

		case STRING_SUBSTRING: { // 15.5.4.15
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 1, 2);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			if (call.getNumberOfArgs() == 1) {
				// ##################################################
				dependency.join(call.getArg(0).getDependency());
				// ##################################################
			}
			else if (call.getNumberOfArgs() == 2) {
				// ##################################################
				dependency.join(call.getArg(0).getDependency());
				dependency.join(call.getArg(1).getDependency());
				// ##################################################
			}
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			// ##################################################
			
			return Value.makeAnyStr(dependency); // TODO: improve precision?
//			NativeFunctions.expectParameters(nativeobject, call, c, 2, 2);
//			Value vthisStr = Conversion.toString(Value.makeObject(call.getThis(state)),c);
//			if (vthisStr.isMaybeAnyStr() || call.isUnknownNumberOfArgs())
//				return Value.makeAnyStr();
//			String sthis = vthisStr.getStr();
//			int result2 = sthis.length();
//			Value vstart = call.getArg(0);
//			Value result3 = Conversion.toInteger(vstart,c);
//			Value vend = call.getArg(1);
//			Value result4 = Conversion.toInteger(vend,c);
//			if (result3.isMaybeAnyNum() || result4.isMaybeAnyNum())
//				return Value.makeAnyNumNotNaNInf();
//			int iresult3 = result3.getNum().intValue();
//			int iresult4 = result4.getNum().intValue();
//			int iresult5 = Math.min(Math.max(iresult3, 0), result2);
//			int iresult6 = Math.min(Math.max(iresult4, 0), result2);
//			int iresult7 = Math.min(iresult5, iresult6);
//			int iresult8 = Math.max(iresult5, iresult6);
//			return Value.makeStr(sthis.substring(iresult7, iresult8));
		}

		case STRING_TOLOWERCASE: // 15.5.4.16
		case STRING_TOUPPERCASE: { // 15.5.4.18
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 0, 0);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			// ##################################################
			
			if (vthisstring.isMaybeSingleStr()) {
				String sthis = vthisstring.getStr();
				if (nativeobject.equals(ECMAScriptObjects.STRING_TOLOWERCASE)
						|| nativeobject.equals(ECMAScriptObjects.STRING_TOLOCALELOWERCASE))
					return Value.makeStr(sthis.toLowerCase(), dependency);
				else
					return Value.makeStr(sthis.toUpperCase(), dependency);
			} else
				return Value.makeAnyStr(dependency);
		}

		case STRING_TOLOCALELOWERCASE: // 15.5.4.17
		case STRING_TOLOCALEUPPERCASE: { // 15.5.4.19
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################
			
			NativeFunctions.expectParameters(nativeobject, call, c, 0, 0);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			// ##################################################
			
			return Value.makeAnyStr(dependency);
		}
		
		case STRING_SUBSTR: { // B.2.3
			// ##################################################
			Dependency dependency = new Dependency();
			// ##################################################

			NativeFunctions.expectParameters(nativeobject, call, c, 1, 2);
			Value vthisstring = state.readInternalValue(state.readThisObjects());
			
			// ##################################################
			dependency.join(vthisstring.getDependency());
			// ##################################################
			
			if (call.getNumberOfArgs() == 1) {
				// ##################################################
				dependency.join(call.getArg(0).getDependency());
				// ##################################################
			}
			else if (call.getNumberOfArgs() == 2) {
				// ##################################################
				dependency.join(call.getArg(0).getDependency());
				dependency.join(call.getArg(1).getDependency());
				// ##################################################
			}
			
			return Value.makeAnyStr(dependency); // TODO: improve precision?
		}
		
		default:
			return null;
		}
	}
}