
var I="http://www.google.com/",H=String.fromCharCode(176),Za="sunnyDay",gb=900000,fb=15000,jb=60000,va=1,hb=2,ib=60;var Ya=108,bb=185,aa=140,P=66,xa=207,wa=108,O=128,sa=255,ta="isExpanded",D,p,ka,n=[],y,u,U=false,W=false,J=false,z=false,ga=false,Xa=5,$a=25,ab=-25;function _onSizing(){var a=event.width,b=(a-aa)/2;b-=Xa;if(b<ab){b-=$a}if(K){if(z){for(var c=0;c<n.length;++c){n[c].t(b)}}event.height=n.length*P}else{if(z){n[0].t(b)}if(!V){event.height=P}}}function Sb(a,b,c){if(a){var d=new s;d.name=b;
if(!c){d.type=s.CITY;d.value=b}else{d.type=s.ZIP;d.value=c}Ra(d,false)}}function Rb(a,b){for(var c=0;c<b.length;++c){var d=b[c];if(d.isDefault){var e=new s;e.type=s.LATLONG;e.name=d.name;var g=d.latitude+","+d.longitude;e.value=g;Ra(e,true);return}}}function Ra(a,b){if(p.locationList.length==1&&p.c(strings.DEFAULT_CITY)===0){p.z(0);p.d(a);p.b=b;la(p);ha();X()}}function _onOptionChanged(){if(options.exists("fisocode")){var a=options.getValue("fisocode");options.remove("fisocode");if(a!=$){ra(a,D,Rb)}return}if(options.exists("fzips")){var b=
options.getValue("fzips");options.remove("fzips");var c=b.split(";");for(var d=0;d<c.length;++d){Sa(c[d],D,Sb)}}}function ha(){if(strings.DEFAULT_USE_METRIC=="true"){options.putDefaultValue(k.IS_METRIC_KEY,true)}else{options.putDefaultValue(k.IS_METRIC_KEY,false)}options.putDefaultValue(k.LOCATIONS_COUNT_KEY,0);options.putDefaultValue(k.CURRENT_INDEX_KEY,0);var a=new k;a.b=false;if(options(k.IS_METRIC_KEY)){a.b=true}var b=options(k.LOCATIONS_COUNT_KEY),c;if(b===0){c=new s;c.name=strings.DEFAULT_CITY;
c.value=strings.DEFAULT_CITY;c.type=s.CITY;a.d(c)}else{for(var d=0;d<b;++d){c=new s;c.name=options.getValue(k.LOCATIONS_NAME_KEY+d);c.value=options.getValue(k.LOCATIONS_VALUE_KEY+d);c.type=options.getValue(k.LOCATIONS_TYPE_KEY+d);c.errors=options.getValue(k.LOCATIONS_ERRORS_KEY+d);var e=options.getValue(k.LOCATIONS_WEATHER_DATA_KEY+d);if(e&&!ga){try{var g=eval("("+e+")"),l=new oa;for(var m in g){l[m]=g[m]}c.lastWeatherData=l}catch(j){debug.trace("could not eval weather data JSON");c.lastWeatherData=
null}}a.d(c)}}a.currentIndex=options.getValue(k.CURRENT_INDEX_KEY);p=a}function la(a){options.putValue(k.IS_METRIC_KEY,a.b);var b=a.locationList.length;for(var c=0;c<b;++c){var d=a.locationList[c];options.putValue(k.LOCATIONS_NAME_KEY+c,d.name);options.putValue(k.LOCATIONS_VALUE_KEY+c,d.value);options.putValue(k.LOCATIONS_TYPE_KEY+c,d.type);options.putValue(k.LOCATIONS_ERRORS_KEY+c,d.errors);if(d.lastWeatherData!==null&&!ga){try{options.putValue(k.LOCATIONS_WEATHER_DATA_KEY+c,d.lastWeatherData.a())}catch(e){debug.error("could not convert weather data to JSON");
options.putValue(k.LOCATIONS_WEATHER_DATA_KEY+c,"")}}else{options.putValue(k.LOCATIONS_WEATHER_DATA_KEY+c,"")}}options.putValue(k.LOCATIONS_COUNT_KEY,b);options.putValue(k.CURRENT_INDEX_KEY,a.currentIndex)}function Ha(){options.putValue(k.CURRENT_INDEX_KEY,p.currentIndex)}function Ea(){if(!u){return}var a=u[p.currentIndex];if(!a){debug.trace("Bad weather index");p.currentIndex=0;Ha()}a=u[p.currentIndex];return a}function yb(){if(K){return}da();if(!u){return}if(u.length==1){debug.warning("onCityClick but only one weather");
return}++p.currentIndex;if(!(p.currentIndex<u.length)){p.currentIndex=0}Ha();C()}function Ib(a){var b=new o(D,p.clone(),ua,Bb);b.show(a)}function Bb(a){la(a);ha();X()}var F=[new f("slateMain","slate_main.png",f.IMAGE),new f("slateClosed","slate_closed.png",f.IMAGE),new f("slateOpen","slate_open.png",f.IMAGE),new f("cloudy","gd_weather_cloudy.png",f.PANEL),new f("flurries","gd_weather_flurries.png",f.PANEL),new f("fog","gd_weather_fog.png",f.PANEL),new f("haze","gd_weather_haze.png",f.PANEL),new f("icy",
"gd_weather_icy.png",f.PANEL),new f("mostlyCloudyDay","gd_weather_mostlyCloudyDay.png",f.PANEL),new f("mostlyCloudyNight","gd_weather_mostlyCloudyNight.png",f.PANEL),new f("mostlySunnyDay","gd_weather_mostlySunnyDay.png",f.PANEL),new f("mostlySunnyNight","gd_weather_mostlySunnyNight.png",f.PANEL),new f("rain","gd_weather_rain.png",f.PANEL),new f("sleet","gd_weather_sleet.png",f.PANEL),new f("snow","gd_weather_snow.png",f.PANEL),new f("storm","gd_weather_storm.png",f.PANEL),new f("sunnyDay","undocked-sunny.png",
f.PANEL),new f("sunnyNight","gd_weather_sunnyNight.png",f.PANEL),new f("thunderstorm","gd_weather_thunderstorm.png",f.PANEL),new f("hoverGlow","hover_glow.png",f.PANEL),new f("chanceOfRainIcon","icon_chanceofrain.png",f.REGULAR),new f("chanceOfSleetIcon","icon_chanceofsleet.png",f.REGULAR),new f("chanceOfSnowIcon","icon_chanceofsnow.png",f.REGULAR),new f("chanceOfStormIcon","icon_chanceofstorm.png",f.REGULAR),new f("chanceOfThunderstormIcon","icon_chanceofthunderstorm.png",f.REGULAR),new f("clearNightIcon",
"icon_clear_night.png",f.REGULAR),new f("cloudyIcon","icon_cloudy.png",f.REGULAR),new f("flurriesIcon","icon_flurries.png",f.REGULAR),new f("fogIcon","icon_fog.png",f.REGULAR),new f("hazeIcon","icon_haze.png",f.REGULAR),new f("icyIcon","icon_icy.png",f.REGULAR),new f("mostlyClearNightIcon","icon_mostlyclear_night.png",f.REGULAR),new f("mostlyCloudyNightIcon","icon_mostlycloudy_night.png",f.REGULAR),new f("mostlyCloudyIcon","icon_mostlycloudy.png",f.REGULAR),new f("mostlySunnyIcon","icon_mostlysunny.png",
f.REGULAR),new f("rainIcon","icon_rain.png",f.REGULAR),new f("snowIcon","icon_snow.png",f.REGULAR),new f("stormIcon","icon_storm.png",f.REGULAR),new f("sunnyIcon","icon_sunny.png",f.REGULAR),new f("thunderstormIcon","icon_thunderstorm.png",f.REGULAR)];function ea(a){for(var b=0;b<F.length;++b){if(F[b].id==a){return F[b]}}}function E(a){return rb()+"\\"+a}function ca(a){var b=framework.system.filesystem;return b.FileExists(E(a))}function rb(){if(!ka){var a=gadget.storage.extract("plugin_small.png"),
b=a.lastIndexOf("\\");ka=a.substring(0,b)}return ka}var ua=5,ya="weatherPanel";function wb(){var a,b=function(c){return function(){Db(c)}};n[0]=new i(ya,0,0,xa,wa);n[0].A(b(0));n[0].Z(yb);for(a=1;a<ua;++a){n[a]=new i(ya+a,0,P*a,aa,P);n[a].A(b(a))}z=true;if(fa){Ja()}else{Ka()}}function vb(a){for(var b=0;b<n.length;++b){n[b].X(a.id,E(a.filename))}}function ub(a){var b=E(a.filename);switch(a.id){case "slateMain":slateMainBkg.src=b;break;case "slateClosed":slateClosedBkg.src=b;break;case "slateOpen":slateOpenBkg.src=
b;break;default:debug.error("Unknown Image resource: "+a.id)}}function nb(){var a=ea(Za),b=a.filename;if(!ca(b)){var c=framework.system.filesystem,d=gadget.storage.extract(b);try{c.copyFile(d,E(b));debug.trace("Copied default layer")}catch(e){debug.error("Unable to copy default layer: "+e.message)}}}function Ba(a){var b;if(a.type==f.PANEL){b=function(c){vb(c)}}else if(a.type==f.IMAGE){b=function(c){ub(c)}}else{b=function(){}}return b}function Hb(){var a=true;for(var b=0;b<F.length;++b){var c=F[b],
d=c.filename;if(!ca(d)){a=false;debug.trace("Must download: "+d);var e=Ba(c);Gb(c,e)}}W=a}function xb(){var a=true;for(var b=0;b<F.length;++b){var c=F[b],d=c.filename;if(ca(d)){var e=Ba(c);e(c)}else{a=false}}W=a}function zb(){U=true;mb()}function mb(){if(U){qb()}}function qb(){plugin.onShowOptionsDlg=Ib}var Y=va;function ja(){Y=va}function lb(){Y=Math.min(Y*hb,ib)}function ia(){var a=jb*Math.random()*Y;view.setTimeout(ba,a)}function ba(){debug.trace("Check if remote data is needed");if(framework.system.network.online===
false){debug.trace("Offline");ja();C();view.setTimeout(ba,fb);return}if(U&&W&&J){debug.trace("Remote data not needed");ja();ia();return}var a=I+"ig/api",b=new XMLHttpRequest;b.onreadystatechange=c;b.open("GET",a,true);b.send();function c(){if(b.readyState!=4){return}if(b.status==200){debug.trace("Appears to be online, retrieve remote data");ja();ia();Fb()}else{debug.warning("Non 200 ping request status: "+b.status);lb();ia();C()}b=null}}function Fb(){if(!U){Wa(D,zb)}if(!W){Hb()}if(!J){X()}}function _onOpen(){plugin.onDisplayTargetChange=
Ab;try{D=system.languageCode()}catch(a){D=strings.DEFAULT_LANGUAGE}ga=framework.runtime.appName=="Web Browser";wb();nb();xb();pb();ha();ba();view.setInterval(X,gb);_onOptionChanged();view.onoptionchanged=_onOptionChanged}function X(){Qb(D,p,Cb,sb)}var cb=4;function Fa(){var a=new Enumerator(forecastPane.children);while(!a.atEnd()){a.item().visible=false;a.moveNext()}}function Jb(){var a=new Enumerator(forecastPane.children);while(!a.atEnd()){a.item().visible=true;a.moveNext()}}function za(){for(var a=
1;a<cb;++a){forecastPane.children("high_temp_"+a).innerText="";forecastPane.children("low_temp_"+a).innerText="";forecastPane.children("date_"+a).innerText="";forecastPane.children("weather_"+a).src="";forecastPane.children("weather_"+a).tooltip=""}}function tb(){slateMainBkg.visible=false;slateOpenBkg.visible=false;slateClosedBkg.visible=false;Fa()}function Ca(){slateMainBkg.visible=true;slateOpenBkg.visible=false;slateClosedBkg.visible=false;if(Ga()){slateOpenBkg.visible=true;slateClosedBkg.visible=
false;Jb();view.height=bb}else{slateOpenBkg.visible=false;slateClosedBkg.visible=true;Fa();view.height=Ya}}function Ga(){return options.getValue(ta)}function Kb(){options.putValue(ta,!Ga());Ca()}var fa=true;function Ia(a){fa=a}function Ab(a){if(a==gddTargetSidebar){debug.trace("docked");Ja()}else if(a==gddTargetFloatingView){debug.trace("not docked");Ka()}}function Ja(){Ia(true);view.resizable=true;view.width=aa;view.height=P;if(z){n[0].B()}tb()}function Ka(){Ia(false);view.resizable=false;view.width=
xa;view.height=wa;if(z){n[0].t(0);n[0].aa()}Ca()}var M=undefined,L=undefined,kb=10000,V=false;function _onMinimize(){V=true;if(z){Ma()}}function _onRestore(){V=false;if(z&&M!==undefined){view.clearInterval(M);M=undefined;view.caption=strings.GADGET_NAME}}function Ma(){if(M!==undefined){view.clearInterval(M)}L=p.currentIndex;Na();M=view.setInterval(Na,kb)}function Na(){if(!u){view.caption=strings.NOT_CONNECTED;return}if(L<0||L>=u.length){L=0}var a=u[L],b="";if(!a.isProblem){b+=p.b?a.currentTempC:a.currentTempF;
b+=H;b+=p.b?"C":"F";b+=" ";b+=a.currentCondition}else{b+=strings.NO_WEATHER_INFORMATION}b+=" - ";b+=a.name;view.caption=b;++L}var K=false;function _onPopOut(){K=true;if(z){C()}}function _onPopIn(){K=false;if(z){C()}}function Db(a){if(fa){if(K){da();La(a)}else{if(y){da()}else{La()}}}else{Kb()}}function da(){if(y){pluginHelper.closeDetailsView()}}function Eb(a){if(a==gddDetailsViewFlagNone){}else if(a==gddDetailsViewFlagToolbarOpen){var b=new ActiveXObject("Shell.Application"),c=y.detailsViewData.getValue("location"),
d=I+"search?hl=en&lr=&q=weather+"+encodeURIComponent(c);b.ShellExecute(d)}y=null}var eb=3;function Aa(a){var b=[],c;if(a.isProblem){for(c=0;c<eb;++c){b.push({})}return b}for(c=1;c<a.forecasts.length;++c){var d={};if(a.forecasts[c].high!=""){d.hi="H "+S(a.forecasts[c].high,a.b())+H}else{d.hi="H --"}if(a.forecasts[c].lo!=""){d.lo="L "+S(a.forecasts[c].low,a.b())+H}else{d.lo="L --"}d.date=(forecastPane.children("date_"+c).innerText=a.forecasts[c].day);var e=h.getWeatherType(a.forecasts[c].icon),g=e.day.icon,
l=ea(g);if(l){var m=e.day.isChanceOf?O:sa;d.iconPath=E(l.filename);d.opacity=m}d.condition=a.forecasts[c].condition;b.push(d)}return b}function La(a){var b;if(a!==undefined){b=u[a]}else{b=Ea()}if(!b){return}var c=b.name,d=Aa(b);y=new DetailsView;y.contentIsView=true;y.detailsViewData.putValue("forecasts",d);y.detailsViewData.putValue("location",b.name);y.setContent(undefined,undefined,"details.xml",false,0);pluginHelper.showDetailsView(y,c,gddDetailsViewFlagToolbarOpen,Eb)}var db=10;function Cb(a){J=
true;u=a;for(var b=0;b<p.locationList.length;++b){var c=p.locationList[b],d=u[b];if(d.isProblem){++c.errors;if(c.errors>=db){}else{debug.trace("error count: "+c.errors);if(c.lastWeatherData!==null){debug.trace("using cached data for: "+c.name);u[b]=c.lastWeatherData}}}else{c.lastWeatherData=d;c.errors=0}}la(p);C()}function C(){if(V){Ma()}var a=Ea();if(J&&a){var b=Aa(a);for(var c=0;c<b.length;++c){var d=c+1,e=b[c];forecastPane.children("high_temp_"+d).innerText=e.hi;forecastPane.children("low_temp_"+
d).innerText=e.lo;forecastPane.children("date_"+d).innerText=e.date;forecastPane.children("weather_"+d).src=e.iconPath;forecastPane.children("weather_"+d).opacity=e.opacity;forecastPane.children("weather_"+d).tooltip=e.condition}}else{za()}if(u&&u.length>1){n[0].k(true)}else{n[0].k(false)}for(c=1;c<n.length;++c){n[c].hide()}if(J){if(!K){n[0].e(false);Da(n[0],a)}else if(u){for(var g=0;g<u.length&&g<n.length;++g){n[g].B();n[g].e(true);Da(n[g],u[g]);n[g].show()}}}else{var l=h.Error;n[0].clear();T(n[0],
l.day.layer,O);n[0].i(strings.NOT_CONNECTED,"");n[0].k(false)}}function T(a,b,c){var d=ea(b);if(!d){debug.error("Could not find resource: "+b);return}a.G(d.id,E(d.filename),c)}function Da(a,b){if(b.isProblem){a.clear();T(a,h.Error.day.layer,O);a.i(Pa(b.name),strings.NO_WEATHER_INFORMATION);a.q("?",strings.NO_WEATHER_INFORMATION);a.p("",strings.NO_WEATHER_INFORMATION);return}var c=h.getWeatherType(b.currentIcon),d=Lb(),e=d?c.day:c.night,g=e.isChanceOf?O:sa;T(a,e.layer,g);var l=ob(b);a.i(Pa(b.name),
l);var m=p.b?b.currentTempC:b.currentTempF;a.q(m+H,l);var j="H",r="L";if(b.forecasts[0].high!=""){j+=S(b.forecasts[0].high,b.b());j+=H}else{j+="--"}if(b.forecasts[0].low!=""){r+=S(b.forecasts[0].low,b.b());r+=H}else{r+="--"}var w=j+"  |  "+r;a.p(w,l);a.$(l)}function ob(a){var b="";b+=a.currentCondition;b+="\n";b+=a.currentWind;b+="\n";b+=a.currentHumidity;return b}function sb(){J=false;C()}function pb(){var a=h.Error,b=n[0];b.clear();T(b,a.day.layer,O);b.i(strings.LOADING,"");b.k(false);za()}function S(a,
b){if(p.b){if(!b){a=Math.round((parseInt(a)-32)*0.5555555555555556)}}else{if(b){a=Math.round(parseInt(a)*1.8+32)}}return a}var h={MostlyCloudy:{day:{icon:"mostlyCloudyIcon",layer:"mostlyCloudyDay"},night:{icon:"mostlyCloudyNightIcon",layer:"mostlyCloudyNight"}},Cloudy:{day:{icon:"cloudyIcon",layer:"cloudy"},night:{icon:"cloudyIcon",layer:"cloudy"}},MostlySunny:{day:{icon:"mostlySunnyIcon",layer:"mostlySunnyDay"},night:{icon:"mostlyClearNightIcon",layer:"mostlySunnyNight"}},Sunny:{day:{icon:"sunnyIcon",
layer:"sunnyDay"},night:{icon:"clearNightIcon",layer:"sunnyNight"}},Fog:{day:{icon:"fogIcon",layer:"fog"},night:{icon:"fogIcon",layer:"fog"}},Haze:{day:{icon:"hazeIcon",layer:"haze"},night:{icon:"hazeIcon",layer:"haze"}},Icy:{day:{icon:"icyIcon",layer:"icy"},night:{icon:"icyIcon",layer:"icy"}},Flurries:{day:{icon:"flurriesIcon",layer:"flurries"},night:{icon:"flurriesIcon",layer:"flurries"}},Snow:{day:{icon:"snowIcon",layer:"snow"},night:{icon:"snowIcon",layer:"snow"}},Rain:{day:{icon:"rainIcon",layer:"rain"},
night:{icon:"rainIcon",layer:"rain"}},Thunderstorm:{day:{icon:"thunderstormIcon",layer:"thunderstorm"},night:{icon:"thunderstormIcon",layer:"thunderstorm"}},Storm:{day:{icon:"stormIcon",layer:"storm"},night:{icon:"stormIcon",layer:"storm"}},ChanceOfSnow:{day:{icon:"chanceOfSnowIcon",layer:"snow",isChanceOf:true},night:{icon:"chanceOfSnowIcon",layer:"snow",isChanceOf:true}},ChanceOfRain:{day:{icon:"chanceOfRainIcon",layer:"rain",isChanceOf:true},night:{icon:"chanceOfRainIcon",layer:"rain",isChanceOf:true}},
ChanceOfSleet:{day:{icon:"chanceOfSleetIcon",layer:"sleet",isChanceOf:true},night:{icon:"chanceOfSleetIcon",layer:"sleet",isChanceOf:true}},ChanceOfStorm:{day:{icon:"chanceOfStormIcon",layer:"storm",isChanceOf:true},night:{icon:"chanceOfStormIcon",layer:"storm",isChanceOf:true}},ChanceOfTStorm:{day:{icon:"chanceOfThunderstormIcon",layer:"thunderstorm",isChanceOf:true},night:{icon:"chanceOfThunderstormIcon",layer:"thunderstorm",isChanceOf:true}},Error:{day:{icon:"sunnyIcon",layer:"sunnyDay",isChanceOf:true},
night:{icon:"sunnyIcon",layer:"sunnyDay",isChanceOf:true}}};h.getWeatherType=function(a){var b="error",c=/.*\/([\w]+).gif$/i,d=a.match(c);if(d){b=d[1]}var e;switch(b.toLowerCase()){case "error":debug.trace("WeatherType.Error: "+a);e=h.Error;break;case "mostly_cloudy":e=h.MostlyCloudy;break;case "cloudy":e=h.Cloudy;break;case "mostly_sunny":e=h.MostlySunny;break;case "sunny":e=h.Sunny;break;case "flurries":e=h.Flurries;break;case "fog":e=h.Fog;break;case "haze":e=h.Haze;break;case "icy":e=h.Icy;break;
case "chance_of_sleet":e=h.ChanceOfSleet;break;case "snow":e=h.Snow;break;case "chance_of_snow":e=h.ChanceOfSnow;break;case "rain":e=h.Rain;break;case "chance_of_rain":e=h.ChanceOfRain;break;case "thunderstorm":e=h.Thunderstorm;break;case "chance_of_tstorm":e=h.ChanceOfTStorm;break;case "storm":e=h.Storm;break;case "chance_of_storm":e=h.ChanceOfStorm;break;case "jp_sunny":e=h.Sunny;break;case "jp_sunnysometimescloudy":e=h.MostlySunny;break;case "jp_sunnysometimesrainy":e=h.ChanceOfRain;break;case "jp_sunnysometimessnowy":e=
h.ChanceOfSnow;break;case "jp_sunnythencloudy":e=h.MostlySunny;break;case "jp_sunnythenrainy":e=h.ChanceOfRain;break;case "jp_sunnythensnowy":e=h.ChanceOfSnow;break;case "jp_cloudy":e=h.Cloudy;break;case "jp_cloudysometimessunny":e=h.MostlyCloudy;break;case "jp_cloudysometimesrainy":e=h.ChanceOfRain;break;case "jp_cloudysometimessnowy":e=h.ChanceOfSnow;break;case "jp_cloudythensunny":e=h.MostlyCloudy;break;case "jp_cloudythenrainy":e=h.ChanceOfRain;break;case "jp_cloudythensnowy":e=h.ChanceOfSnow;
break;case "jp_rainy":e=h.Rain;break;case "jp_rainysometimessunny":e=h.ChanceOfRain;break;case "jp_rainysometimescloudy":e=h.ChanceOfRain;break;case "jp_rainysometimessnowy":e=h.Rain;break;case "jp_rainythensunny":e=h.ChanceOfRain;break;case "jp_rainythencloudy":e=h.ChanceOfRain;break;case "jp_rainythensnowy":e=h.Rain;break;case "jp_snowy":e=h.Snow;break;case "jp_snowysometimessunny":e=h.ChanceOfSnow;break;case "jp_snowysometimescloudy":e=h.ChanceOfSnow;break;case "jp_snowysometimesrainy":e=h.Snow;
break;case "jp_snowythensunny":e=h.ChanceOfSnow;break;case "jp_snowythencloudy":e=h.ChanceOfSnow;break;case "jp_snowythenrainy":e=h.Snow;break;default:e=h.Sunny;break}return e};