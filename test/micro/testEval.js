var a = eval();
dumpValue(a);
var b = eval(9.11);
dumpValue(b);
var c = eval(new Boolean(b));
dumpValue(c);
var d = eval("function f(x) { f(x); }");
dumpValue(d);
dumpObject(d);
var e = d.a;
dumpValue(e);
var f = d(1,2,3,4);
dumpValue(f);