
function Mb(a){this.msg=a}if(!Object.prototype.a){Array.prototype.a=function(){var a=["["],b,c,d=this.length,e;function g(l){if(b){a.push(",")}a.push(l);b=true}for(c=0;c<d;c+=1){e=this[c];switch(typeof e){case "object":if(e){if(typeof e.a==="function"){g(e.a())}}else{g("null")}break;case "string":case "number":case "boolean":g(e.a())}}a.push("]");return a.join("")};Boolean.prototype.a=function(){return String(this)};Date.prototype.a=function(){function a(b){return b<10?"0"+b:b}return'"'+this.getFullYear()+
"-"+a(this.getMonth()+1)+"-"+a(this.getDate())+"T"+a(this.getHours())+":"+a(this.getMinutes())+":"+a(this.getSeconds())+'"'};Number.prototype.a=function(){return isFinite(this)?String(this):"null"};Object.prototype.a=function(){var a=["{"],b,c,d;function e(g){if(b){a.push(",")}a.push(c.a(),":",g);b=true}for(c in this){if(this.hasOwnProperty(c)){d=this[c];switch(typeof d){case "object":if(d){if(typeof d.a==="function"){e(d.a())}}else{e("null")}break;case "string":case "number":case "boolean":e(d.a())}}}a.push("}");
return a.join("")};(function(a){var b={"\u0008":"\\b","\t":"\\t","\n":"\\n","\u000c":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"};a.parseJSON=function(c){try{if(/^("(\\.|[^"\\\n\r])*?"|[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t])+?$/.test(this)){var d=eval("("+this+")");if(typeof c==="function"){function e(l,m){if(m&&typeof m==="object"){for(var j in m){if(m.hasOwnProperty(j)){m[j]=e(j,m[j])}}}return c(l,m)}d=e("",d)}return d}}catch(g){}throw new Mb("parseJSON");};a.a=function(){if(/["\\\x00-\x1f]/.test(this)){return'"'+
this.replace(/([\x00-\x1f\\"])/g,function(c,d){var e=b[d];if(e){return e}e=d.charCodeAt();return"\\u00"+Math.floor(e/16).toString(16)+(e%16).toString(16)})+'"'}return'"'+this+'"'}})(String.prototype)};
