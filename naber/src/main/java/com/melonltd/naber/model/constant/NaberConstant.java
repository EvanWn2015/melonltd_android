package com.melonltd.naber.model.constant;

import com.google.common.collect.Lists;

import java.util.List;

public class NaberConstant {
    public static boolean IS_DEBUG = true;

    public static long REMEMBER_DAY = 1000 * 60 * 60 * 24 * 7L * 2;
//    public static long SELLER_STAT_REFRESH_TIMER = IS_DEBUG ? 1000 * 10L : 1000 * 60 * 60 * 10L;
//    public static long SELLER_ORDER_REFRESH_TIMER = IS_DEBUG ? 1000 * 10L : 1000 * 60 * 60 * 10L;

    public static long SELLER_STAT_REFRESH_TIMER =  1000 * 60 * 10L;
//    public static long SELLER_LIVE_ORDER_REFRESH_TIMER = 1000 * 60 * 5L;
    public static long SELLER_LIVE_ORDER_REFRESH_TIMER = 1000 * 5L;
//    public static long SELLER_LIVE_ORDER_REFRESH_TIMER = 1000 * 10L;
    public static int PAGE = 10;

    public static int ORDER_MAX_AMOUNT = 5000;

    public static String SCHOOL_DIVIDED_TEMP  = "[{\"area\":\"桃園市\",\"schools\":[\"中原大學\",\"長庚大學\",\"元智大學\",\"開南大學\",\"銘傳大學\",\"萬能科大\"]}]";
    public static String SUBJECTION_REGION = "[{\"city\":\"臺北市\",\"areas\":[{\"area\":\"中正區\",\"postal_code\":\"100\"},{\"area\":\"大同區\",\"postal_code\":\"103\"},{\"area\":\"中山區\",\"postal_code\":\"104\"},{\"area\":\"松山區\",\"postal_code\":\"105\"},{\"area\":\"大安區\",\"postal_code\":\"106\"},{\"area\":\"萬華區\",\"postal_code\":\"108\"},{\"area\":\"信義區\",\"postal_code\":\"110\"},{\"area\":\"士林區\",\"postal_code\":\"111\"},{\"area\":\"北投區\",\"postal_code\":\"112\"},{\"area\":\"內湖區\",\"postal_code\":\"114\"},{\"area\":\"南港區\",\"postal_code\":\"115\"},{\"area\":\"文山區\",\"postal_code\":\"116\"}]},{\"city\":\"基隆市\",\"areas\":[{\"area\":\"仁愛區\",\"postal_code\":\"200\"},{\"area\":\"信義區\",\"postal_code\":\"201\"},{\"area\":\"中正區\",\"postal_code\":\"202\"},{\"area\":\"中山區\",\"postal_code\":\"203\"},{\"area\":\"安樂區\",\"postal_code\":\"204\"},{\"area\":\"暖暖區\",\"postal_code\":\"205\"},{\"area\":\"七堵區\",\"postal_code\":\"206\"}]},{\"city\":\"新北市\",\"areas\":[{\"area\":\"萬里區\",\"postal_code\":\"207\"},{\"area\":\"金山區\",\"postal_code\":\"208\"},{\"area\":\"板橋區\",\"postal_code\":\"220\"},{\"area\":\"汐止區\",\"postal_code\":\"221\"},{\"area\":\"深坑區\",\"postal_code\":\"222\"},{\"area\":\"石碇區\",\"postal_code\":\"223\"},{\"area\":\"瑞芳區\",\"postal_code\":\"224\"},{\"area\":\"平溪區\",\"postal_code\":\"226\"},{\"area\":\"雙溪區\",\"postal_code\":\"227\"},{\"area\":\"貢寮區\",\"postal_code\":\"228\"},{\"area\":\"新店區\",\"postal_code\":\"231\"},{\"area\":\"坪林區\",\"postal_code\":\"232\"},{\"area\":\"烏來區\",\"postal_code\":\"233\"},{\"area\":\"永和區\",\"postal_code\":\"234\"},{\"area\":\"中和區\",\"postal_code\":\"235\"},{\"area\":\"土城區\",\"postal_code\":\"236\"},{\"area\":\"三峽區\",\"postal_code\":\"237\"},{\"area\":\"樹林區\",\"postal_code\":\"238\"},{\"area\":\"鶯歌區\",\"postal_code\":\"239\"},{\"area\":\"三重區\",\"postal_code\":\"241\"},{\"area\":\"新莊區\",\"postal_code\":\"242\"},{\"area\":\"泰山區\",\"postal_code\":\"243\"},{\"area\":\"林口區\",\"postal_code\":\"244\"},{\"area\":\"蘆洲區\",\"postal_code\":\"247\"},{\"area\":\"五股區\",\"postal_code\":\"248\"},{\"area\":\"八里區\",\"postal_code\":\"249\"},{\"area\":\"淡水區\",\"postal_code\":\"251\"},{\"area\":\"三芝區\",\"postal_code\":\"252\"},{\"area\":\"石門區\",\"postal_code\":\"253\"}]},{\"city\":\"連江縣\",\"areas\":[{\"area\":\"南竿鄉\",\"postal_code\":\"209\"},{\"area\":\"北竿鄉\",\"postal_code\":\"210\"},{\"area\":\"莒光鄉\",\"postal_code\":\"211\"},{\"area\":\"東引鄉\",\"postal_code\":\"212\"}]},{\"city\":\"宜蘭縣\",\"areas\":[{\"area\":\"宜蘭市\",\"postal_code\":\"260\"},{\"area\":\"頭城鎮\",\"postal_code\":\"261\"},{\"area\":\"礁溪鄉\",\"postal_code\":\"262\"},{\"area\":\"壯圍鄉\",\"postal_code\":\"263\"},{\"area\":\"員山鄉\",\"postal_code\":\"264\"},{\"area\":\"羅東鎮\",\"postal_code\":\"265\"},{\"area\":\"三星鄉\",\"postal_code\":\"266\"},{\"area\":\"大同鄉\",\"postal_code\":\"267\"},{\"area\":\"五結鄉\",\"postal_code\":\"268\"},{\"area\":\"冬山鄉\",\"postal_code\":\"269\"},{\"area\":\"蘇澳鎮\",\"postal_code\":\"270\"},{\"area\":\"南澳鄉\",\"postal_code\":\"272\"},{\"area\":\"釣魚臺\",\"postal_code\":\"290\"}]},{\"city\":\"新竹市\",\"areas\":[{\"area\":\"東區\",\"postal_code\":\"300\"},{\"area\":\"北區\",\"postal_code\":\"300\"},{\"area\":\"香山區\",\"postal_code\":\"300\"}]},{\"city\":\"新竹縣\",\"areas\":[{\"area\":\"竹北市\",\"postal_code\":\"302\"},{\"area\":\"湖口鄉\",\"postal_code\":\"303\"},{\"area\":\"新豐鄉\",\"postal_code\":\"304\"},{\"area\":\"新埔鎮\",\"postal_code\":\"305\"},{\"area\":\"關西鎮\",\"postal_code\":\"306\"},{\"area\":\"芎林鄉\",\"postal_code\":\"307\"},{\"area\":\"寶山鄉\",\"postal_code\":\"308\"},{\"area\":\"竹東鎮\",\"postal_code\":\"310\"},{\"area\":\"五峰鄉\",\"postal_code\":\"311\"},{\"area\":\"橫山鄉\",\"postal_code\":\"312\"},{\"area\":\"尖石鄉\",\"postal_code\":\"313\"},{\"area\":\"北埔鄉\",\"postal_code\":\"314\"},{\"area\":\"峨眉鄉\",\"postal_code\":\"315\"}]},{\"city\":\"桃園市\",\"areas\":[{\"area\":\"中壢區\",\"postal_code\":\"320\"},{\"area\":\"平鎮區\",\"postal_code\":\"324\"},{\"area\":\"龍潭區\",\"postal_code\":\"325\"},{\"area\":\"楊梅區\",\"postal_code\":\"326\"},{\"area\":\"新屋區\",\"postal_code\":\"327\"},{\"area\":\"觀音區\",\"postal_code\":\"328\"},{\"area\":\"桃園區\",\"postal_code\":\"330\"},{\"area\":\"龜山區\",\"postal_code\":\"333\"},{\"area\":\"八德區\",\"postal_code\":\"334\"},{\"area\":\"大溪區\",\"postal_code\":\"335\"},{\"area\":\"復興區\",\"postal_code\":\"336\"},{\"area\":\"大園區\",\"postal_code\":\"337\"},{\"area\":\"蘆竹區\",\"postal_code\":\"338\"}]},{\"city\":\"苗栗縣\",\"areas\":[{\"area\":\"竹南鎮\",\"postal_code\":\"350\"},{\"area\":\"頭份市\",\"postal_code\":\"351\"},{\"area\":\"三灣鄉\",\"postal_code\":\"352\"},{\"area\":\"南庄鄉\",\"postal_code\":\"353\"},{\"area\":\"獅潭鄉\",\"postal_code\":\"354\"},{\"area\":\"後龍鎮\",\"postal_code\":\"356\"},{\"area\":\"通霄鎮\",\"postal_code\":\"357\"},{\"area\":\"苑裡鎮\",\"postal_code\":\"358\"},{\"area\":\"苗栗市\",\"postal_code\":\"360\"},{\"area\":\"造橋鄉\",\"postal_code\":\"361\"},{\"area\":\"頭屋鄉\",\"postal_code\":\"362\"},{\"area\":\"公館鄉\",\"postal_code\":\"363\"},{\"area\":\"大湖鄉\",\"postal_code\":\"364\"},{\"area\":\"泰安鄉\",\"postal_code\":\"365\"},{\"area\":\"銅鑼鄉\",\"postal_code\":\"366\"},{\"area\":\"三義鄉\",\"postal_code\":\"367\"},{\"area\":\"西湖鄉\",\"postal_code\":\"368\"},{\"area\":\"卓蘭鎮\",\"postal_code\":\"369\"}]},{\"city\":\"臺中市\",\"areas\":[{\"area\":\"中區\",\"postal_code\":\"400\"},{\"area\":\"東區\",\"postal_code\":\"401\"},{\"area\":\"南區\",\"postal_code\":\"402\"},{\"area\":\"西區\",\"postal_code\":\"403\"},{\"area\":\"北區\",\"postal_code\":\"404\"},{\"area\":\"北屯區\",\"postal_code\":\"406\"},{\"area\":\"西屯區\",\"postal_code\":\"407\"},{\"area\":\"南屯區\",\"postal_code\":\"408\"},{\"area\":\"太平區\",\"postal_code\":\"411\"},{\"area\":\"大里區\",\"postal_code\":\"412\"},{\"area\":\"霧峰區\",\"postal_code\":\"413\"},{\"area\":\"烏日區\",\"postal_code\":\"414\"},{\"area\":\"豐原區\",\"postal_code\":\"420\"},{\"area\":\"后里區\",\"postal_code\":\"421\"},{\"area\":\"石岡區\",\"postal_code\":\"422\"},{\"area\":\"東勢區\",\"postal_code\":\"423\"},{\"area\":\"和平區\",\"postal_code\":\"424\"},{\"area\":\"新社區\",\"postal_code\":\"426\"},{\"area\":\"潭子區\",\"postal_code\":\"427\"},{\"area\":\"大雅區\",\"postal_code\":\"428\"},{\"area\":\"神岡區\",\"postal_code\":\"429\"},{\"area\":\"大肚區\",\"postal_code\":\"432\"},{\"area\":\"沙鹿區\",\"postal_code\":\"433\"},{\"area\":\"龍井區\",\"postal_code\":\"434\"},{\"area\":\"梧棲區\",\"postal_code\":\"435\"},{\"area\":\"清水區\",\"postal_code\":\"436\"},{\"area\":\"大甲區\",\"postal_code\":\"437\"},{\"area\":\"外埔區\",\"postal_code\":\"438\"},{\"area\":\"大安區\",\"postal_code\":\"439\"}]},{\"city\":\"彰化縣\",\"areas\":[{\"area\":\"彰化市\",\"postal_code\":\"500\"},{\"area\":\"芬園鄉\",\"postal_code\":\"502\"},{\"area\":\"花壇鄉\",\"postal_code\":\"503\"},{\"area\":\"秀水鄉\",\"postal_code\":\"504\"},{\"area\":\"鹿港鎮\",\"postal_code\":\"505\"},{\"area\":\"福興鄉\",\"postal_code\":\"506\"},{\"area\":\"線西鄉\",\"postal_code\":\"507\"},{\"area\":\"和美鎮\",\"postal_code\":\"508\"},{\"area\":\"伸港鄉\",\"postal_code\":\"509\"},{\"area\":\"員林市\",\"postal_code\":\"510\"},{\"area\":\"社頭鄉\",\"postal_code\":\"511\"},{\"area\":\"永靖鄉\",\"postal_code\":\"512\"},{\"area\":\"埔心鄉\",\"postal_code\":\"513\"},{\"area\":\"溪湖鎮\",\"postal_code\":\"514\"},{\"area\":\"大村鄉\",\"postal_code\":\"515\"},{\"area\":\"埔鹽鄉\",\"postal_code\":\"516\"},{\"area\":\"田中鎮\",\"postal_code\":\"520\"},{\"area\":\"北斗鎮\",\"postal_code\":\"521\"},{\"area\":\"田尾鄉\",\"postal_code\":\"522\"},{\"area\":\"埤頭鄉\",\"postal_code\":\"523\"},{\"area\":\"溪州鄉\",\"postal_code\":\"524\"},{\"area\":\"竹塘鄉\",\"postal_code\":\"525\"},{\"area\":\"二林鎮\",\"postal_code\":\"526\"},{\"area\":\"大城鄉\",\"postal_code\":\"527\"},{\"area\":\"芳苑鄉\",\"postal_code\":\"528\"},{\"area\":\"二水鄉\",\"postal_code\":\"530\"}]},{\"city\":\"南投縣\",\"areas\":[{\"area\":\"南投市\",\"postal_code\":\"540\"},{\"area\":\"中寮鄉\",\"postal_code\":\"541\"},{\"area\":\"草屯鎮\",\"postal_code\":\"542\"},{\"area\":\"國姓鄉\",\"postal_code\":\"544\"},{\"area\":\"埔里鎮\",\"postal_code\":\"545\"},{\"area\":\"仁愛鄉\",\"postal_code\":\"546\"},{\"area\":\"名間鄉\",\"postal_code\":\"551\"},{\"area\":\"集集鎮\",\"postal_code\":\"552\"},{\"area\":\"水里鄉\",\"postal_code\":\"553\"},{\"area\":\"魚池鄉\",\"postal_code\":\"555\"},{\"area\":\"信義鄉\",\"postal_code\":\"556\"},{\"area\":\"竹山鎮\",\"postal_code\":\"557\"},{\"area\":\"鹿谷鄉\",\"postal_code\":\"558\"}]},{\"city\":\"嘉義市\",\"areas\":[{\"area\":\"西區\",\"postal_code\":\"600\"},{\"area\":\"東區\",\"postal_code\":\"600\"}]},{\"city\":\"嘉義縣\",\"areas\":[{\"area\":\"番路鄉\",\"postal_code\":\"602\"},{\"area\":\"梅山鄉\",\"postal_code\":\"603\"},{\"area\":\"竹崎鄉\",\"postal_code\":\"604\"},{\"area\":\"阿里山鄉\",\"postal_code\":\"605\"},{\"area\":\"中埔鄉\",\"postal_code\":\"606\"},{\"area\":\"大埔鄉\",\"postal_code\":\"607\"},{\"area\":\"水上鄉\",\"postal_code\":\"608\"},{\"area\":\"鹿草鄉\",\"postal_code\":\"611\"},{\"area\":\"太保市\",\"postal_code\":\"612\"},{\"area\":\"朴子市\",\"postal_code\":\"613\"},{\"area\":\"東石鄉\",\"postal_code\":\"614\"},{\"area\":\"六腳鄉\",\"postal_code\":\"615\"},{\"area\":\"新港鄉\",\"postal_code\":\"616\"},{\"area\":\"民雄鄉\",\"postal_code\":\"621\"},{\"area\":\"大林鎮\",\"postal_code\":\"622\"},{\"area\":\"溪口鄉\",\"postal_code\":\"623\"},{\"area\":\"義竹鄉\",\"postal_code\":\"624\"},{\"area\":\"布袋鎮\",\"postal_code\":\"625\"}]},{\"city\":\"雲林縣\",\"areas\":[{\"area\":\"斗南鎮\",\"postal_code\":\"630\"},{\"area\":\"大埤鄉\",\"postal_code\":\"631\"},{\"area\":\"虎尾鎮\",\"postal_code\":\"632\"},{\"area\":\"土庫鎮\",\"postal_code\":\"633\"},{\"area\":\"褒忠鄉\",\"postal_code\":\"634\"},{\"area\":\"東勢鄉\",\"postal_code\":\"635\"},{\"area\":\"臺西鄉\",\"postal_code\":\"636\"},{\"area\":\"崙背鄉\",\"postal_code\":\"637\"},{\"area\":\"麥寮鄉\",\"postal_code\":\"638\"},{\"area\":\"斗六市\",\"postal_code\":\"640\"},{\"area\":\"林內鄉\",\"postal_code\":\"643\"},{\"area\":\"古坑鄉\",\"postal_code\":\"646\"},{\"area\":\"莿桐鄉\",\"postal_code\":\"647\"},{\"area\":\"西螺鎮\",\"postal_code\":\"648\"},{\"area\":\"二崙鄉\",\"postal_code\":\"649\"},{\"area\":\"北港鎮\",\"postal_code\":\"651\"},{\"area\":\"水林鄉\",\"postal_code\":\"652\"},{\"area\":\"口湖鄉\",\"postal_code\":\"653\"},{\"area\":\"四湖鄉\",\"postal_code\":\"654\"},{\"area\":\"元長鄉\",\"postal_code\":\"655\"}]},{\"city\":\"臺南市\",\"areas\":[{\"area\":\"中西區\",\"postal_code\":\"700\"},{\"area\":\"東區\",\"postal_code\":\"701\"},{\"area\":\"南區\",\"postal_code\":\"702\"},{\"area\":\"北區\",\"postal_code\":\"704\"},{\"area\":\"安平區\",\"postal_code\":\"708\"},{\"area\":\"安南區\",\"postal_code\":\"709\"},{\"area\":\"永康區\",\"postal_code\":\"710\"},{\"area\":\"歸仁區\",\"postal_code\":\"711\"},{\"area\":\"新化區\",\"postal_code\":\"712\"},{\"area\":\"左鎮區\",\"postal_code\":\"713\"},{\"area\":\"玉井區\",\"postal_code\":\"714\"},{\"area\":\"楠西區\",\"postal_code\":\"715\"},{\"area\":\"南化區\",\"postal_code\":\"716\"},{\"area\":\"仁德區\",\"postal_code\":\"717\"},{\"area\":\"關廟區\",\"postal_code\":\"718\"},{\"area\":\"龍崎區\",\"postal_code\":\"719\"},{\"area\":\"官田區\",\"postal_code\":\"720\"},{\"area\":\"麻豆區\",\"postal_code\":\"721\"},{\"area\":\"佳里區\",\"postal_code\":\"722\"},{\"area\":\"西港區\",\"postal_code\":\"723\"},{\"area\":\"七股區\",\"postal_code\":\"724\"},{\"area\":\"將軍區\",\"postal_code\":\"725\"},{\"area\":\"學甲區\",\"postal_code\":\"726\"},{\"area\":\"北門區\",\"postal_code\":\"727\"},{\"area\":\"新營區\",\"postal_code\":\"730\"},{\"area\":\"後壁區\",\"postal_code\":\"731\"},{\"area\":\"白河區\",\"postal_code\":\"732\"},{\"area\":\"東山區\",\"postal_code\":\"733\"},{\"area\":\"六甲區\",\"postal_code\":\"734\"},{\"area\":\"下營區\",\"postal_code\":\"735\"},{\"area\":\"柳營區\",\"postal_code\":\"736\"},{\"area\":\"鹽水區\",\"postal_code\":\"737\"},{\"area\":\"善化區\",\"postal_code\":\"741\"},{\"area\":\"大內區\",\"postal_code\":\"742\"},{\"area\":\"山上區\",\"postal_code\":\"743\"},{\"area\":\"新市區\",\"postal_code\":\"744\"},{\"area\":\"安定區\",\"postal_code\":\"745\"}]},{\"city\":\"高雄市\",\"areas\":[{\"area\":\"新興區\",\"postal_code\":\"800\"},{\"area\":\"前金區\",\"postal_code\":\"801\"},{\"area\":\"苓雅區\",\"postal_code\":\"802\"},{\"area\":\"鹽埕區\",\"postal_code\":\"803\"},{\"area\":\"鼓山區\",\"postal_code\":\"804\"},{\"area\":\"旗津區\",\"postal_code\":\"805\"},{\"area\":\"前鎮區\",\"postal_code\":\"806\"},{\"area\":\"三民區\",\"postal_code\":\"807\"},{\"area\":\"楠梓區\",\"postal_code\":\"811\"},{\"area\":\"小港區\",\"postal_code\":\"812\"},{\"area\":\"左營區\",\"postal_code\":\"813\"},{\"area\":\"仁武區\",\"postal_code\":\"814\"},{\"area\":\"大社區\",\"postal_code\":\"815\"},{\"area\":\"東沙群島\",\"postal_code\":\"817\"},{\"area\":\"南沙群島\",\"postal_code\":\"819\"},{\"area\":\"岡山區\",\"postal_code\":\"820\"},{\"area\":\"路竹區\",\"postal_code\":\"821\"},{\"area\":\"阿蓮區\",\"postal_code\":\"822\"},{\"area\":\"田寮區\",\"postal_code\":\"823\"},{\"area\":\"燕巢區\",\"postal_code\":\"824\"},{\"area\":\"橋頭區\",\"postal_code\":\"825\"},{\"area\":\"梓官區\",\"postal_code\":\"826\"},{\"area\":\"彌陀區\",\"postal_code\":\"827\"},{\"area\":\"永安區\",\"postal_code\":\"828\"},{\"area\":\"湖內區\",\"postal_code\":\"829\"},{\"area\":\"鳳山區\",\"postal_code\":\"830\"},{\"area\":\"大寮區\",\"postal_code\":\"831\"},{\"area\":\"林園區\",\"postal_code\":\"832\"},{\"area\":\"鳥松區\",\"postal_code\":\"833\"},{\"area\":\"大樹區\",\"postal_code\":\"840\"},{\"area\":\"旗山區\",\"postal_code\":\"842\"},{\"area\":\"美濃區\",\"postal_code\":\"843\"},{\"area\":\"六龜區\",\"postal_code\":\"844\"},{\"area\":\"內門區\",\"postal_code\":\"845\"},{\"area\":\"杉林區\",\"postal_code\":\"846\"},{\"area\":\"甲仙區\",\"postal_code\":\"847\"},{\"area\":\"桃源區\",\"postal_code\":\"848\"},{\"area\":\"那瑪夏區\",\"postal_code\":\"849\"},{\"area\":\"茂林區\",\"postal_code\":\"851\"},{\"area\":\"茄萣區\",\"postal_code\":\"852\"}]},{\"city\":\"澎湖縣\",\"areas\":[{\"area\":\"馬公市\",\"postal_code\":\"880\"},{\"area\":\"西嶼鄉\",\"postal_code\":\"881\"},{\"area\":\"望安鄉\",\"postal_code\":\"882\"},{\"area\":\"七美鄉\",\"postal_code\":\"883\"},{\"area\":\"白沙鄉\",\"postal_code\":\"884\"},{\"area\":\"湖西鄉\",\"postal_code\":\"885\"}]},{\"city\":\"金門縣\",\"areas\":[{\"area\":\"金沙鎮\",\"postal_code\":\"890\"},{\"area\":\"金湖鎮\",\"postal_code\":\"891\"},{\"area\":\"金寧鄉\",\"postal_code\":\"892\"},{\"area\":\"金城鎮\",\"postal_code\":\"893\"},{\"area\":\"烈嶼鄉\",\"postal_code\":\"894\"},{\"area\":\"烏坵鄉\",\"postal_code\":\"896\"}]},{\"city\":\"屏東縣\",\"areas\":[{\"area\":\"屏東市\",\"postal_code\":\"900\"},{\"area\":\"三地門鄉\",\"postal_code\":\"901\"},{\"area\":\"霧臺鄉\",\"postal_code\":\"902\"},{\"area\":\"瑪家鄉\",\"postal_code\":\"903\"},{\"area\":\"九如鄉\",\"postal_code\":\"904\"},{\"area\":\"里港鄉\",\"postal_code\":\"905\"},{\"area\":\"高樹鄉\",\"postal_code\":\"906\"},{\"area\":\"鹽埔鄉\",\"postal_code\":\"907\"},{\"area\":\"長治鄉\",\"postal_code\":\"908\"},{\"area\":\"麟洛鄉\",\"postal_code\":\"909\"},{\"area\":\"竹田鄉\",\"postal_code\":\"911\"},{\"area\":\"內埔鄉\",\"postal_code\":\"912\"},{\"area\":\"萬丹鄉\",\"postal_code\":\"913\"},{\"area\":\"潮州鎮\",\"postal_code\":\"920\"},{\"area\":\"泰武鄉\",\"postal_code\":\"921\"},{\"area\":\"來義鄉\",\"postal_code\":\"922\"},{\"area\":\"萬巒鄉\",\"postal_code\":\"923\"},{\"area\":\"崁頂鄉\",\"postal_code\":\"924\"},{\"area\":\"新埤鄉\",\"postal_code\":\"925\"},{\"area\":\"南州鄉\",\"postal_code\":\"926\"},{\"area\":\"林邊鄉\",\"postal_code\":\"927\"},{\"area\":\"東港鎮\",\"postal_code\":\"928\"},{\"area\":\"琉球鄉\",\"postal_code\":\"929\"},{\"area\":\"佳冬鄉\",\"postal_code\":\"931\"},{\"area\":\"新園鄉\",\"postal_code\":\"932\"},{\"area\":\"枋寮鄉\",\"postal_code\":\"940\"},{\"area\":\"枋山鄉\",\"postal_code\":\"941\"},{\"area\":\"春日鄉\",\"postal_code\":\"942\"},{\"area\":\"獅子鄉\",\"postal_code\":\"943\"},{\"area\":\"車城鄉\",\"postal_code\":\"944\"},{\"area\":\"牡丹鄉\",\"postal_code\":\"945\"},{\"area\":\"恆春鎮\",\"postal_code\":\"946\"},{\"area\":\"滿州鄉\",\"postal_code\":\"947\"}]},{\"city\":\"臺東縣\",\"areas\":[{\"area\":\"臺東市\",\"postal_code\":\"950\"},{\"area\":\"綠島鄉\",\"postal_code\":\"951\"},{\"area\":\"蘭嶼鄉\",\"postal_code\":\"952\"},{\"area\":\"延平鄉\",\"postal_code\":\"953\"},{\"area\":\"卑南鄉\",\"postal_code\":\"954\"},{\"area\":\"鹿野鄉\",\"postal_code\":\"955\"},{\"area\":\"關山鎮\",\"postal_code\":\"956\"},{\"area\":\"海端鄉\",\"postal_code\":\"957\"},{\"area\":\"池上鄉\",\"postal_code\":\"958\"},{\"area\":\"東河鄉\",\"postal_code\":\"959\"},{\"area\":\"成功鎮\",\"postal_code\":\"961\"},{\"area\":\"長濱鄉\",\"postal_code\":\"962\"},{\"area\":\"太麻里鄉\",\"postal_code\":\"963\"},{\"area\":\"金峰鄉\",\"postal_code\":\"964\"},{\"area\":\"大武鄉\",\"postal_code\":\"965\"},{\"area\":\"達仁鄉\",\"postal_code\":\"966\"}]},{\"city\":\"花蓮縣\",\"areas\":[{\"area\":\"花蓮市\",\"postal_code\":\"970\"},{\"area\":\"新城鄉\",\"postal_code\":\"971\"},{\"area\":\"秀林鄉\",\"postal_code\":\"972\"},{\"area\":\"吉安鄉\",\"postal_code\":\"973\"},{\"area\":\"壽豐鄉\",\"postal_code\":\"974\"},{\"area\":\"鳳林鎮\",\"postal_code\":\"975\"},{\"area\":\"光復鄉\",\"postal_code\":\"976\"},{\"area\":\"豐濱鄉\",\"postal_code\":\"977\"},{\"area\":\"瑞穗鄉\",\"postal_code\":\"978\"},{\"area\":\"萬榮鄉\",\"postal_code\":\"979\"},{\"area\":\"玉里鎮\",\"postal_code\":\"981\"},{\"area\":\"卓溪鄉\",\"postal_code\":\"982\"},{\"area\":\"富里鄉\",\"postal_code\":\"983\"}]}]";

//    public static final String[] FILTER_CATEGORYS = new String[]{"火鍋", "燒烤/居酒屋", "鐵板燒", "素蔬食", "早午餐", "下午茶", "西式/牛排", "中式", "港式", "日式", "韓式", "異國", "美式", "義式", "熱炒", "小吃", "泰式", "咖啡輕食", "甜點", "冰飲"};
    public static String[] FILTER_CATEGORYS = new String[]{"早午餐", "西式/牛排", "中式", "日式", "冰飲"};
//    public static final String[] FILTER_AREAS = new String[]{"桃園區", "中壢區", "平鎮區", "龍潭區", "楊梅區", "新屋區", "觀音區", "龜山區", "八德區", "大溪區", "大園區", "蘆竹區", "復興區"};
    public static String[] FILTER_AREAS = new String[]{"桃園區", "中壢區", "平鎮區","楊梅區", "龜山區", "八德區", "蘆竹區"};
    public static List<String> HOUR_OPT = Lists.newArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    public static List<String> MINUTE_OPT = Lists.newArrayList("00", "30");

    public static final String FIREBASE_ACCOUNT = "naber_android@gmail.com";
    public static final String FIREBASE_PSW = "melonltd1102";
    public static final String STORAGE_PATH = "gs://naber-20180622.appspot.com";
    public static final String STORAGE_PATH_USER = "/user/";
    public static final String STORAGE_PATH_FOOD = "/restaurant/food/";

    // Bundle key
    public static final String RESTAURANT_INFO = "RESTAURANT_INFO";
    public static final String CATEGORY_NAME = "CATEGORY_NAME";
    public static final String RESTAURANT_CATEGORY_REL = "RESTAURANT_CATEGORY_REL";
    public static final String ORDER_INFO = "ORDER_INFO";
    public static final String FOOD_INFO = "FOOD_INFO";
    public static final String SIMPLE_INFO = "USER_SIMPLE_INFO";
    public static final String TOOLBAR_TITLE = "TOOLBAR_TITLE";
    public static final String ACCOUNT_INFO = "ACCOUNT_INFO";



    public static final String ORDER_DETAIL_INDEX = "ORDER_DETAIL_INDEX";
    public static final String SELLER_STAT_LOGS_DETAIL = "SELLER_STAT_LOGS_DETAIL";
    public static final String SELLER_CATEGORY_NAME = "SELLER_CATEGORY_NAME";
    public static final String SELLER_CATEGORY_UUID = "SELLER_CATEGORY_UUID";
    public static final String SELLER_FOOD_INFO = "SELLER_FOOD_INFO";


    //
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'";
}
