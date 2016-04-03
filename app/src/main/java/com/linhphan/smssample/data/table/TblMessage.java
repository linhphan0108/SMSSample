package com.linhphan.smssample.data.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.linhphan.androidboilerplate.data.table.BaseTable;
import com.linhphan.androidboilerplate.util.Logger;

/**
 * Created by linh on 29/03/2016.
 */
public class TblMessage extends BaseTable {

    public static final String TBL_NAME = "tbl_message";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CAT_ID = "cat_id";
    public static final String COLUMN_LANG_ID = "lang_id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TRANSLATION = "translation";
    public static final String COLUMN_STARED = "stared";

    @Override
    public void onUpgrade(Context context, SQLiteDatabase database, int oldVersion, int newVersion) {
        Logger.i(getClass().getName(), "on upgrade (old version: "+ oldVersion +", new version: "+ newVersion +")");
    }

    @Override
    protected String getCreationSqlStatement() {
        Logger.i(getClass().getName(), "on create");
        return "CREATE TABLE IF NOT EXISTS " + TBL_NAME +
                "(" +
                COLUMN_ID +" INTEGER PRIMARY KEY autoincrement, "+
                COLUMN_CAT_ID +" INTEGER, "+
                COLUMN_LANG_ID +" INTEGER, "+
                COLUMN_CONTENT +" TEXT NOT NULL, "+
                COLUMN_TRANSLATION +" TEXT, "+
                COLUMN_STARED +" BOOLEAN, "+
                "FOREIGN KEY ("+COLUMN_CAT_ID+") REFERENCES "+ TblCategory.TBL_NAME +"("+ TblCategory.COLUMN_ID+"), "+
                "FOREIGN KEY ("+COLUMN_LANG_ID+") REFERENCES "+ TblLanguage.TBL_NAME +"("+ TblLanguage.COLUMN_ID+")"+
                ")";
    }

    @Override
    protected void populateData(Context context, SQLiteDatabase database) {
        ContentValues values = new ContentValues(5);

        //========================== vietnamese ====================================================
        //=== love
        values.put(COLUMN_LANG_ID, TblLanguage.VI_ID);
        values.put(COLUMN_CAT_ID, TblCategory.LOVE_ID);
        values.put(COLUMN_CONTENT, "Em bị triệu đến toà án vì đã bước vào ước mơ của anh, đánh cắp trái tim anh, và cướp đi mọi cảm xúc trong anh. Em bị kết án ở bên anh mãi mãi. Em có gì biện hộ không?");
        values.put(COLUMN_TRANSLATION, "");
        values.put(COLUMN_STARED, false);
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh chọn yêu em trong câm lặng vì trong câm lặng anh không bị cự tuyệt. anh chọn yêu em trong sự cô đơn của em vì trong sự cô đơn đó chẳng ai có thể có em cả ngoại trừ anh.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Người ta bảo yêu là có tội, anh chẳng bao giờ hỏi vì sao. nhưng nếu có tội khi yêu em, anh sẽ phạm tội cho tới chết.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh chẳng sợ gì cả trên đời này, thậm chí còn ngạo nghễ cười trước cái chết. thế nhưng khi nhìn vào mắt em, anh đã sợ. anh sợ anh yêu em biết bao nhiêu.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chắc chắn trên biển cả thật nhiều cá nhưng em là loại duy nhất ăn phải con sâu của anh.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Cưng à, nếu tình yêu có cảm giác như thế này, cho anh viên thuốc giảm đau nhé!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Này thằng bé nói thật nhé, cả đời thằng bé chỉ can đảm được một lần thôi. Đó là yêu con bé đấy!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Em là điều đã xảy ra khi anh đang ước có một ngôi sao");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Đừng nhăn mặt như thế em à, vì em không biết có người tim đập sai nhịp vì nụ cười em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Khi cô đơn và sợ hãi, anh đã xin Thượng đế gửi cho anh một thiên thần. Người quá chừng tốt bụng khi gửi em cho anh.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Số phận cho anh gặp em. Chọn lựa khiến anh trở thành bạn của em. Còn yêu em? Điều này vượt qua tầm kiểm soát của anh rồi.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Không có món quà nào quá nhỏ để trao tặng, cũng như quá đơn giản để nhận như món quà được gói bằng sự suy nghĩ và cột bằng tình yêu.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh nghĩ thánh Valentine là một người đàn ông tuyệt vời khi ông cho anh cơ hội nói tiếng yêu em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Trong phép tính của tình yêu, 1 + 1 = tất cả, còn 2 – 1 = 0.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Nếu bị ép buộc phải nói vì sao anh yêu em, anh chỉ có thể trả lời: “Vì đó là em. Vì đó là tôi!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh đã chẳng bao giờ biết đến sự sùng bái cho tới khi anh biết anh yêu em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "3 – 2 = 1 trái tim cầu nguyện cho em; 1 + 1= 2 con mắt tìm em; 3+2= 5 giác quan nhớ em; 5+2= 7 ngày trong tuần mơ về em; 7+5= 12 tháng cầu phúc cho em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Thú thật với em, anh không có lời lẽ để khiến em yên tâm hơn nhưng anh có đôi tay ôm em thật chặt, có đôi tai để nghe những gì em tâm sự và có trái tim, một trái tim luôn mong thấy nụ cười em trong ngày này.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ta cùng chia sẻ thế giới nào. Biển cả của em, sóng gào của anh. Trời xanh của em, sao mờ của anh. Mặt trời của em, tia sáng của anh. Mọi thứ của em, và em của anh.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Gửi em chiếc giường để em yên ngủ, chiếc gối để em thoải mái và chiếc chăn để em ấm áp. Anh chẳng thể ngủ được vì anh đã gửi cho em tất cả những gì anh có. Chúc em ngủ ngon. Anh yêu em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Dành một chỗ cho anh trong trái tim em chứ không phải trong đầu em vì trí óc dễ quên nhưng trái tim luôn nhớ. Anh yêu em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh yêu tất cả sao trên bầu trời nhưng chúng có nghĩa gì đâu khi so sánh với những ngôi sao trong mắt em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Nói cho anh biết em được cấu thành từ những chất gì? Bởi vì anh đã trộn 100kg đường với 80kg sô cô là và 60 kg mật ong, thế mà chẳng thể tạo ra thứ gì ngọt hơn em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Có những người chết sớm vì ông trời yêu họ nhiều quá… Em biết không, em vẫn còn tồn tại trên đời này vì có người đã yêu em nhiều hơn ông trời đấy.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Nếu nụ hôn là nước, anh sẽ cho em biển cả. Nếu nắm tay nhau là lá anh sẽ cho em cả rừng cây.Nếu em yêu một hành tinh anh sẽ cho em cả thiên hà. Nếu yêu như là sống, anh sẽ cho em cả cuộc đời này.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Em có biết không, nụ hôn là khoảng cách ngắn nhất giữa hai người đấy!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Nếu em ở trong một căn phòng tối, nhìn thấy xung quanh đều là máu và những bức tường đang rung chuyển, thì đừng lo sợ nhé, em yêu. Em đang ở một nơi an toàn nhất. đó là trái tim anh.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chiếc chìa khoá nhỏ có thể mở ổ khoá lớn, lời nói đơn giản có thể biểu lộ tư tưởng vĩ đại và chỉ một tin nhắn của em sẽ khiến anh vui cười suốt cả ngày. Nhớ em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Thi thoảng mắt anh ghen tị với tim anh. Em có biết vì sao không? Vì em luôn kề cận với trái tim anh và xa khỏi tầm mắt anh đấy.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Một nụ hôn chỉ là một nụ hôn cho tới khi em tìm thấy người em yêu. Một cái nắm tay chỉ là một cái nắm tay khi em tìm thấy người mà em luôn nghĩ đến. Một giấc mơ mãi chỉ là giấc mơ cho tới khi nó thành sự thật. Và tình yêu chỉ là lời nói cho tới khi anh nghe thấy nó từ em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Nếu hoa hồng màu đen và violet màu nâu thì tình yêu của anh dành cho em sẽ không bao giờ được nhận thấy. Nhưng hoa hồng màu đỏ và violet màu xanh, nên tất cả những gì anh muốn nói là anh yêu em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ước gì giờ này anh ở bên em, ôm em thật chặt thay vì nói câu này: “Chúc em ngủ ngon!”");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Thầy Toán dạy anh 1 giờ = 60 ph, 1 phút = 60 giây, nhưng thầy chẳng nói với anh 1 giây không có em lại bằng tới 100 năm. Nhớ em!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Trái tim đập rộn ràng… Tâm trí ghi nhớ mãi… Sa mạc có thể thay bằng đại dương. Nhưng không gì có thể ngăn được nụ cười khi anh thấy tên em hiện trên máy anh.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Mặt trăng nhìn anh và hỏi ” nếu người yêu bạn không gửi tin nhắn, bạn có bỏ cô ấy không? Anh trả lời “Thế bầu trời có bỏ bạn không nếu bạn không chiếu sáng?” Hoan nghinh anh đi nào!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh sẽ xuống địa ngục vì tội lỗi của mình nhưng anh muốn đi cùng em, bởi vì chỉ có em mới có thể biến địa ngục thành thiên đường với sự ngọt ngào của mình!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Hãy để giấc mơ tươi đẹp nhất đến với em tối nay. Hãy để con người ngọt ngào nhất bờc vào giấc mộng ấy tối nay…. Nhưng đừng mong đây là thói quen, vì anh không phải tối nào cũng rảnh…");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Trời ạ, anh tin em vô cùng nhưng tại sao em lại đi kể cho người khác nghe bí mật của anh? Tại sao gặp ai em cũng bảo anh là người ngọt ngào nhất?");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Đừng nói yêu anh nếu như em không muốn, bởi vì có lẽ anh sẽ làm điều gì đó ngu ngốc… như tin vào tình yêu đó chẳng hạn.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh nhìn thấy mặt trời lặn và nghĩ đến em. Anh nhìn thấy tất cả và nghĩ đến em. Một cái hẹn, một cơ hội là những gì anh mong muốn. đến với anh em nhé.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Bảng chữ cái bắt đầu bằng ABC. Số đếm bắt đầu bằng 123. Tình yêu bắt đầu bằng anh và em." +
                "42. Đam mê là một từ bao gồm rất nhiều cảm xúc. Anh nhận ra điều này, khi ta nắm tay nhau, khi ta hôn nhau, khi anh nhìn em. Bởi vì em là đam mê của anh.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Đố em noh ssiW nghĩa là gì? Chỉ cần xoay ngược lại điện thoại, em sẽ biết ngay. Anh nhớ em!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh sẽ viết lên trên tất cả các viên gạch dòng chữ anh nhớ em và anh ước gì một viên sẽ rơi trúng đầu em, để em biết được nỗi đau như nào khi nhớ đến người đặc biệt như em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Mỗi lúc anh nhớ em, một ngôi sao rụng xuống. Nếu em nhìn lên trời và chẳng thấy ngôi sao nào cả, bởi vì em bắt anh nhớ em quá nhiều.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Người ở gần thì không yêu. Người thân yêu thì không ở gần. Nhớ người ở gần sao quá dễ. Quên người thân yêu sao khó quá.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Trắng: Tĩnh lặng. Xanh: tươi mát. Đỏ: nồng nàn. Hồng: Đáng yêu. Tím: chung thuỷ. Nâu: chín chắn. Đen: Thông minh. Vàng: Quý phái. Anh là màu nào trong trái tim em?");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ngày chẳng có ý nghĩa gì nếu không có mặt trời. Đêm chẳng có ý nghĩa gì nếu thiếu mặt trăng. Biển chẳng có ý nghĩa gì nếu không có nước. Anh có ý nghĩa gì nếu thiếu em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Nếu em đang muốn khóc: Hãy gọi anh. Anh không làm em cười được nhưng có thể khóc cùng em. Nếu em muốn bỏ đi xa: Hãy gọi anh. Anh không yêu cầu em ở lại nhưng có thể đi cùng em. Nếu em muốn nghe: Hãy gọi anh và anh hứa sẽ cùng yên lặng với em. Nhưng… nếu một ngày em gọi anh và không ai trả lời. Hãy đến nhanh với anh. Bởi vì anh đang cần em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Đừng khép cửa khi em cô đơn. Đừng khép lòng khi em muốn tình yêu. Đừng khép đôi tay khi em cần anh. Vì anh sẽ ôm em cho đến hết đời.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "A B C D E F G H I J K L M N O P Q R S T U… Z. Người sáng tạo ra bảng chữ cái là một thiên tài nhưng cũng hết sức ngốc nghếch khi đặt “U” và “I” quá xa.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Hút thuốc mỗi ngày sẽ chết sớm 10 năm. Uống rượu mỗi ngày sẽ chết sớm 30 năm. Nhưng yêu em,… Anh đang chết mỗi ngày đấy. Đáng thương chưa?");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Có 12 tháng một năm…30 ngày một tháng…7 ngày một tuần…24 giờ một ngày…60 phút một giờ…Nhưng chỉ có một là em thôi trong đời anh.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Có 2 lí do tại sao anh thức dậy vào buổi sáng: Đồng hồ báo thức và em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Những tư tưởng lớn có các ý tưởng, giải pháp, và nguyên nhân; những bộ óc khoa học có các công thức, lý thuyết và con số; tâm trí anh chỉ có mình em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Tình yêu có thể biểu lộ bằng nhiều cách. Một cách anh biết đó là gửi tình yêu băng qua khoảng không gian đến với người đang đọc tin nhắn này.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Có Đêm nên mới quý Ngày. Có Buồn nên mới trân trọng Vui. Có Ác nên mới mong Thiện. Có Em nên anh mới mong Tình Yêu.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, " Hôm nay trông em tuyệt vời quá. Vì sao anh biết à? Bởi vì em lúc nào mà không tuyệt vời!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Tình yêu là gì? Người không thích gọi là trách nhiệm. Người chơi bời gọi là game. Người không có gọi là giấc mơ. Người hiểu biết gọi là số phận. Còn anh, anh gọi tình yêu là em." +
                "60. Tình yêu là gì? Tình yêu chính là điều khiến điện thoại của em lên tiếng chuông mỗi khi anh gửi tin nhắn.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Nếu chỉ cần nhắm mắt lại là có thể tránh được tình yêu thì anh sẽ không một nháy mắt vì không muốn để mỗi giây trôi qua đánh mất tình yêu từ em..");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Có những người có năng khiếu trời cho. Họ có thể làm nhiều điều tốt đẹp bằng kỹ năng, kiến thức và chuyên môn của mình. Nhưng không ai có thể tài năng bằng em. Chỉ cần em đến gần, sắc đẹp đã hiển hiện.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Điện thoại di động lắm lúc khiến ta bực mình lắm. tiền cước nè, xạc pin nè, thi thoảng tin nhắn lại bị kẹt. Nhưng có một điều khiến anh yêu nó: nó kết nối anh và em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Từ ngữ bắt đầu bằng ABC. Số đếm bắt đầu bằng 123. Âm nhạc bắt đầu bằng Do, Re, Mi. Tình yêu bắt đầu bằng anh và em!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Mưa và nắng thường không cùng xuất hiện. Ngày và đêm chẳng bao giờ trùng phùng. Nhưng anh và em, bất kể người ta nói gì, vẫn luôn thật xứng hợp.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Có hàng ngàn cây số giữa chúng ta. Núi, rừng, sông, suối ngăn cách. Anh chẳng phải là siêu nhân nhưng hãy cho anh một phút nhé. Anh sẽ gửi vào không gian để đến em tình yêu của anh. Em đã nhận được chưa?");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh đang thực thi nhiệm vụ: “dụ dỗ” em. Nói một cách khác, nhiệm vụ bất khả thi!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh ước là giọt lệ trên mắt em, để lăn tròn trên má và đọng lại trên môi em. Nhưng anh không bao giờ muốn em là giọt lệ trong mắt anh vì anh sẽ đánh mất em ngay mỗi lần anh khóc.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Khi trời mưa, em chẳng thấy mặt trời nhưng mặt trời vẫn ở đó. Hi vọng chúng ta cũng như thế. Chúng ta không thường xuyên nhìn thấy nhau nhưng chúng ta vẫn nghĩ về nhau.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Có lẽ anh sẽ hết tin nhắn đẹp để gửi cho em. Có lẽ anh cũng sẽ hết chuyện vui dành cho em. Có lẽ điện thoại anh hết pin hay thẻ chẳng còn tiền. Thế nhưng trái tim anh sẽ không bao giờ hết chỗ dành cho em.");
        database.insert(TBL_NAME, null, values);

        //=== night greetings for girls
        values.put(COLUMN_CAT_ID, TblCategory.NIGHT_GREETING_GIRL_ID);
        values.put(COLUMN_CONTENT, "Hãy đem tất cả những niềm vui của ngày hôm nay vào giấc ngủ thật ngon để mơthấy thật nhiều giấc mơ hạnh phúc em nhé!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ngủ ngon nha em yêu! Chúc em có một giấc thật ngon và nhiều mộng đẹp em nha. Cầu mong giấc ngủ thật ngon và bình an cho em yêu!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Đêm nay anh không biết nhắn cho em gì hết! Anh chỉ biết bây giờ anh nhớ em rất nhiều, tuy nơi đây trời mưa và rất lạnh nhưng anh chỉ mong em có giấc ngủ thật ngon ấm áp trong những giấc mơ hồng, em nhé!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chúc người iu bé nhỏ của anh ngủ thật ngoan cho… cả thế giới đc yên ^^!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Cũng khuya rùi đó em… ko lo đi ngủ đi… Còn ngồi đó mà đọc tin nhắn ^^! ( nếu nàng trả lời thì xơi thêm 1 câu nữa “ ơ … thế nãy giờ vẫn chưa chịu đi ngủ àh” …^^ )");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chúc em đêm nay gặp thật nhiều ác mộng… Và anh sẽ hiện ra để cùng em chạy trốn! ^^");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ban ngày nhìn em sao mà xấu thế… xấu dzã man xấu quất luôn… nhưng khi em ngủ nhìn em rất xinh và thật đáng iu… ngủ đi em… chúc em ngủ ngon!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh nhắn tin cho em… chỉ muốn chúc em ngủ ngon thôi. Hãy ngủ thật ngon vào em nhé! Không thì phí mất 400đ tiền nhắn tin của anh ^^");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chúc em ngủ thật ngon… 1 giấc mơ an lành và ấm áp…. anh sẽ luôn yêu em… Mãi mãi yêu em ( tin nhắn có giá trị trong 7 ngày… vì tuần sau anh mới nhắn lại… còn tuần này, mỗi tối em cứ lấy ra đọc lại nhé… đỡ tốn tiền của 2 chúng mình…Good Night!).");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Em có bít tại sao tối nào 2 đứa mình cũng cần phải ngủ không ? Vì… Như thế mới có chúng ta…hjhj… hẹn em trong mơ… thiên thần của anh!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Em là lí do m&agraagrave; có đêm anh ko ngủ được\n" +
                "Em là lí do mà anh ôm chặt gối vào ban đêm\n" +
                "Và em cũng là lí do mà anh không thể ngủ khi chưa nói “chúc em ngủ ngon”\n" +
                "Ngủ ngon nha cô bé….");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Một khuôn mặt đặc biệt\n" +
                "Một nụ cười đặc biệt\n" +
                "Một người bạn đặc biệt, anh tìm thấy ở em\n" +
                "Ngủ ngon và có những giấc mơ đẹp nhé, cô bạn của anh!!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chúc ngủ ngon không chỉ để chấm dứt một ngày.\n" +
                "Mà nó còn nói rằng, anh nhớ gấu trúc!!!!\n" +
                "Hy vọng em có thể cảm thấy được điều đó!!!!\n" +
                "Chúc ngủ ngon, bạn gấu trúc của tui!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chờ đã\n" +
                "Đừng\n" +
                "Ngủ\n" +
                "Hãy để anh cầu nguyện\n" +
                "Chúc ngủ ngon người bạn cực kì đặc biệt của tôi, cầu cho em được thư giãn\n" +
                "Và ngày mai mang lại cho em yên bình, hạnh phúc và niềm vui!!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Không cần bận tâm những gì em đã mất\n" +
                "Chỉ cần trân trọng những thứ đang có\n" +
                "Vì quá khứ không bao giờ quay trở lại\n" +
                "Nhưng đôi khi tương lại có thể\n" +
                "Cho em lại những thứ đã mất\n" +
                "Chúc em ngủ ngon!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Bé ơi ngủ ngoan đêm đã khuya rồi, để những giấc mơ đẹp sẽ theo em, à ơi à ơi…. Chúc bé ngủ ngon.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chào mặt trăng, bạn gấu trúc của tôi sắp đi ngủ rồi. Nói với mặt trời hãy dậy muộn một chút. Bởi vì bạn gấu trúc muốn nghỉ ngơi thêm nữa.\n" +
                "Chúc em ngủ ngon nhé gấu trúc.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Em biết không lúc này có 3,7 triệu người đang ngủ, 2,3 triệu người đang yêu, 4,1 triệu người đang ăn. Và chỉ có một người trên thế giới này đang đọc tin nhắn của anh. Chúc em ngủ ngon nhé gấu trúc!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Dưới đây là mong mong muốn đặc biệt của anh dành cho em đêm nay:\n" +
                "* Một đêm yên bình, một giấc ngủ ngon.\n" +
                "* Một giấc mơ đẹp, và lời nguyện ước sẽ trở thành sự thật.\n" +
                "Chúc em ngủ ngon!!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Tin nhắn chúc ngủ ngon không chỉ là một lời chúc.\n" +
                "Mà nó còn im lặng nói rằng, gấu trúc là người mà anh nghĩ đến cuối cùng trong đêm.\n" +
                "Chúc em ngủ ngon, gấu trúc!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Sau một ngày mệt mỏi, hứa với anh là em sẽ quên mọi muộn phiền. Để chỉ giữ lại những gì ngọt ngào vào giấc ngủ thôi nhé!!!! Chúc em ngủ ngon!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Nhìn đồng hồ anh biết đã đến giờ đi ngủ, anh nhắn tin chỉ để nói rằng “chúc em ngủ ngon” dù anh biết giờ này hẳn em vẫn chưa ngủ đâu nhỉ!!!\n" +
                "Ngủ ngon nha cô bé đáng yêu!!!!\n" +
                "\n" +
                "Tin nhắn chúc ngủ ngon hay nhất ‘gửi phát yêu liền’ – 3\n" +
                "Tin nhan chuc ngu ngon hay, lãng mạn và hài hước nhất!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "SMS1: Em có thể làm giúp anh một việc được ko?\n" +
                "\n" +
                "SMS2: Việc thứ nhất: tối nay hãy ngủ thật ngon, việc thứ 2 sáng mai thức dậy với một nụ cười trên môi vì anh luôn muốn em vui vẻ. Chúc em ngủ ngon");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chúc ngủ ngon cũng là một nghệ thuật. người chúc ngủ ngon là một nghệ sĩ nhưng người được chúc ngủ ngon hôm nay lại là một người dễ mến đáng yêu chúc em ngủ ngon nhé cô bé đáng yêu.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Đừng bao giờ bỏ cuộc khi em nản lòng\n" +
                "Dù cho nó xảy ra bao nhiêu lần\n" +
                "Chỉ cần em nhớ rằng mỗi khi như vậy\n" +
                "Anh sẽ không bao giờ để em cô đơn\n" +
                "Hãy tin tưởng anh, anh luôn bên cạnh em.\n" +
                "\n" +
                "Chúc em ngủ ngon!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Một ngày của anh không thể kết thúc nếu không có điều gì đó để làm\n" +
                "Anh sẽ không thể ngủ mà không nói rằng :”chúc em ngủ ngon”");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Đêm nay hơi lạnh, trong căn phòng nhỏ\n" +
                "Anh nhìn ngôi sao sáng trên bầu trời tối đen\n" +
                "Và mơ về nụ cười ngọt ngào trên khuôn mặt dễ thương.\n" +
                "Chúc em ngủ ngon!!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Bất cứ khi nào có một ước mơ trong tim em.\n" +
                "Hãy giữ lấy nó, bởi nó là hạt giống nhỏ\n" +
                "Sẽ nảy mầm vào một buổi sớm tươi đẹp\n" +
                "Chúc em có một giấc mơ tuyệt vời đêm nay.\n" +
                "Ngủ ngon nhé gấu trúc!!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Bất cứ khi nào có một ước mơ trong tim em.\n" +
                "Hãy giữ lấy nó, bởi nó là hạt giống nhỏ\n" +
                "Sẽ nảy mầm vào một buổi sớm tươi đẹp\n" +
                "Chúc em có một giấc mơ tuyệt vời đêm nay.\n" +
                "Ngủ ngon nhé gấu trúc!!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Khi anh không thể chạm đến mọi người\n" +
                "Những người ở gần trái tim anh\n" +
                "Anh sẽ ôm lấy với lời cầu nguyện.\n" +
                "Cầu cho em có được những gì em muốn,\n" +
                "Và xin cho em luôn hạnh phúc\n" +
                "Chúc em ngủ ngon!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Đừng bao giờ than phiền về một ngày\n" +
                "Ngày tốt đem lại niềm hạnh phúc\n" +
                "Ngày xấu cho bạn kinh nghiệm\n" +
                "Cả hai đều thiết yếu cho cuộc sống!!!\n" +
                "Chúc em ngủ ngon!!!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ngày của anh có thể bận rộn. Nhưng anh sẽ không bao giờ để hết ngày mà không nói: ”Chúc em ngủ ngon”");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Trong đêm tuyệt vời này, anh cầu nguyện ánh trăng huyền diệu hãy che chở cho em, ngọn gió thơm hãy thổi đi những ưu phiền và những ngôi sao lấp lánh dẫn đường cho những giấc mơ ngọt ngào. Ngon giấc nhé bé yêu!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Trên thiên đường có 10 thiên thần: 5 thiên thần đang chơi đùa, 4 thiên thần đang nói chuyện và 1 thiên thần đang đọc tin nhắn này… Chúc ngủ ngon thiên thần!^^");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Em à ! em đã ngủ chưa? Trời lạnh quá em nhỉ ! Chiếc gối sưởi ấm của em có còn ấm không. Có lẽ sẽ chẳng ấm như tình yêu của anh dành cho em đâu……..Yêu và nhớ em nhiều nhiều. Good night!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ngày hôm nay anh đã chúc em ngủ ngon chưa nhỉ, có lẽ vì nụ cười của em mà anh đã quên tất cả rồi chăng, em hãy giữ mãi nụ cười đó và ngủ thật ngon nha ! hihi");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Đêm nay anh không biết nhắn cho em gì hết ! anh chỉ biết bây giờ anh nhớ em rất nhiều, tuy nơi đây trời mưa và rất lạnh nhưng anh chỉ mong em có giấc ngủ thật ngon ấm áp trong những giấc mơ hồng…… em nhé !");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Nằm trên giường và nhìn đồng hồ, anh biết rằng đã đến giờ đi ngủ. Nhưng anh vẫn phân vân không biết hôm nay em thế nào?? Mong rằng mọi chuyện đều ổn. Chúc em có những giấc mơ ngọt ngào và ngon giấc.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Tớ có nhờ 1 vài thiên thần đến canh giấc ngủ cho ấy. nhưng bọn họ đều từ chối hết, ấy biết sao ko? Họ nói thiên thần ko thể canh cho thiên thần được vì vậy ấy hãy cố ngủ thật ngon nha. Chúc ấy ngủ ngon.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Châu Âu ngủ, Châu Á cũng đang ngủ, Châu Mỹ đang tối dần, chỉ có đôi mắt đẹp nhất trên thế giới này đang đọc tin nhắn của anh. Chúc tình yêu của anh ngủ thật ngon và mơ về anh em nhé!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Khi sao đã tắt, chị Hằng đã mở mắt, Ôm em thật chặt 1 lần nữa. Nhắm mắt vào ngủ thôi bé yêu. Chúc bé ngủ ngon và trong giấc mơ ngọt ngào có anh nhé!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Mùa đông này anh sẽ không còn lạnh nữa, vì em đã sưởi ấm trái tim anh rồi. Anh muốn được là vệ sĩ đứng canh cho những giấc mơ của em đêm nay. Chúc ngủ ngon!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Em yêu ah, đến giờ đi ngủ rồi đó. Nhớ là đừng có thức khuya xem phim nhé phải giữ gìn sức khỏe mai còn đi học chứ! Ngoan anh mới yêu.hj. Chúc bé ngoan của anh ngủ thật ngon na!");
        database.insert(TBL_NAME, null, values);

        //=== night greetings for boys
        values.put(COLUMN_CAT_ID, TblCategory.NIGHT_GREETING_BOY_ID);
        values.put(COLUMN_CONTENT, "Anh ơi ngủ đi! Chị vịt ngốc của anh đã ngủ rất ngon rồi. Chị ấy thương anh lắm đó! Kí tên: điện thoại");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ạnh có thể làm giúp em một việc được ko? Việc đầu tiên: tối nay hãy ngủ thật ngon. Việc thứ 2: sáng mai thức dậy với nụ cười trên môi vì em luôn muốn anh vui vẻ, tràn đầy hạnh phúc. Việc thứ 3: Yêu em và chỉ có mình em :X. Ngủ ngon ông xã à!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Nếu vẫn còn buồn trong lòng, anh hãy gọi đến số 09……. Đó chính là số tổng đài 1080 của riêng anh đấy. Chúc anh yêu ngủ ngon!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Một miếng đá cho trái dâu thêm ngọt, một sợi dây cho quả bóng bay cao, một que diêm cho cây nến sáng tỏ. Và tin nhắn này để thấy nụ cười anh! Hj. Chúc tình yêu của em ngủ ngon nhé!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Alo. Anh đã ngủ chưa thế? Em nằm mãi mà ứ ngủ được, chẳng biết là do đêm lạnh quá hay là nỗi nhớ anh dâng trào. Chưa ngủ thì nhắn tin lại cho em nhé còn không thì chúc anh iu ngủ ngon na. Iu và nhớ anh nhìu! Chụt…");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Đừng khép cửa khi anh thấy cô đơn, đừng khép lòng khi anh muốn tình yêu và đừng khép đôi tay khi anh cần em vì em sẽ ôm anh thật chặt cho đến hết cuộc đời. Ngủ ngon anh iu nhé! Ôm anh!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Một ngày dài mệt mỏi, vất vả sắp trôi qua. Anh phải hứa với em rằng sẽ quẳng hết mọi muộn phiền, lo lắng trước khi đặt mình lên giường anh nhé, hãy để lại những điều ngọt ngào, ấm áp theo anh vào giấc ngủ. Anh ngủ ngon nhé!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Có người nói thật khó để nhớ, còn có người lại nói thật khó để quên. Vì thế nhớ đừng quên em và đừng quên nhớ em ngay cả trong giấc mơ anh nhé! Chúc gấu của em ngủ ngon và mơ về tình yêu lãng mạn của chúng ta nhé! Kiss you…");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chàng ngốc của em à! Anh có biết trên thế giới đang có 4,7 triệu người đang ngủ, 2,3 triệu người đang yêu, 4,1 triệu người đang ăn. Nhưng chỉ có duy nhất 1 người đang đọc tin nhắn của em. Chúc chàng hoàng tử của lòng em ngủ ngon na!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Em đang nhìn ra ngoài cửa sổ nghĩ về người mà em quan tâm nhất và người mà đang ở trong tâm trí em. Đó là anh! Vì thế em chỉ muốn chúc anh ngủ ngon thôi!!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chắc ấy đang ngủ ngon lắm nhỉ. Người ta không ngủ được. Nhớ đằng ấy quá. Mong tới sáng để được gặp ấy quá. Hix. Miss U. Ngu ngon nhé đằng ấy!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ngủ ngon anh yêu nhé. Ngày mới sẽ đến với anh với thật nhiều niềm vui cùng nụ hôn của em kèm trong đó…");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "1 cái “anh yêu ngủ ngon”\n" +
                "1 cái “chồng yêu ngủ ngon”\n" +
                "1 cái “ox của em ngủ ngon”\n" +
                "1 cái “ù pa ngủ ngon”\n" +
                "1 cái “Ty của em ngủ ngon nhá”\n" +
                "1 cái nữa giống cái 1: “anh yêu ngủ ngon nhÁ! hun phát >>>cái này thô bỉ nhất…. s");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ck ngủ ngon nhá..\n" +
                "đừng có mơ tới đứa nào khác:…\n" +
                "Vk iêu Ck nhiều lắm.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Chúc anh ngủ ngon và mơ những giấc mơ đẹp nhất… và 1 ngày mới sẽ đến với anh trong niềm vui và hạnh phúc . Yêu anh nhất trên đời ^^");
        database.insert(TBL_NAME, null, values);

        //===
        values.put(COLUMN_CAT_ID, TblCategory.VALENTINE_ID);
        values.put(COLUMN_CONTENT, "Anh yêu! Em yêu anh không vì anh đẹp trai, không vì anh giàu có … mà đơn giản là em yêu anh chứ không vì một lý do nào khác cả. Em không biết sẽ dành tặng cho anh món quà gì vì em nghĩ rằng chúng sẽ không thể hiện hết tất cả tình cảm em dành cho anh.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ngọt ngào hơn những viên kẹo Đáng yêu hơn những đoá hồng Muốn ôm nhiều hơn cả những món đồ chơi mềm mại Ước mong một ngày lễ tình nhân… Ngọt ngào như là chính em vậy…");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Em xin lỗi anh hôm nay em bị hạn chế nhắn tin cho anh rồi… Hôm nay khám bệnh, bác sĩ bảo em phải hạn chế với những gì ngọt ngọt… mà anh lại ngọt ngào số một. Em đang liều mạng với tin nhắn này đấy. Happy Valentine’s Day. Những lời chúc Valentine lãng mạn nhất");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh sẽ luôn nắm tay em ở chỗ đông người, không phải vì sợ em lạc mất, mà để mọi người nhìn vào trầm trồ rằng “hai đứa nó đang yêu nhau đấy”.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Nếu có bản án dành cho anh vì đã yêu em thì anh xin đứng trước toà và nhận bản án chung thân được bên em suốt đời…");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Vậy là một năm đã trôi qua, một năm dài anh không còn nhận được những lời yêu thương và bàn tay chăm sóc của em. Chúng ta đã chia tay rồi nhưng hằn sâu trong ký ức của anh là những tháng ngày ngọt ngào và hạnh phúc khi ta ở bên nhau.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Giờ đây tuy hai ta đã mỗi người mỗi ngả, nhưng anh vẫn cầu chúc em sẽ nhận được những bó hoa rực rỡ, những nụ hôn ấm áp cùng những thanh sô-cô-la ngọt ngào trong ngày tình yêu. Anh cầu chúc em luôn được hạnh phúc bên người ấy, điều mà anh chưa kịp trao gửi đến em.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh đã đợi từ rất lâu một Valentine như thế, để yêu em và thực sự được em yêu. Một Valentine của riêng hai đứa mình, dẫu không hoa hồng, không sô-cô-la, nhưng vẫn quá đủ với anh bởi có hai trái tim yêu thương trọn vẹn…");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Mùa Valentine đầu tiên anh có em. Yêu em nhiều!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Tôi có một trái tim và nó là sự thật..Nhưng giờ đây nó đã đi theo em. Hãy quan tâm đến nó như anh đã từng làm em nhé..Bởi vì anh ko có nó nữa rồi..Và em đã có cả 2.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Trong mắt anh, em nhìn thấy ngày mai…Trong vòng tay của anh, em tìm thấy tình yêu. Yêu anh hôm nay và mãi mãi.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh yêu! Lần đầu gặp anh, em chỉ thấy đôi mắt của anh một màu xanh sâu thẳm. Và khi anh nhìn em, em có cảm giác như có một cơn sóng bao la cuốn em đi mãi xa. Từ cái ngày hợp nhất chúng mình ấy, không ngày nào em không nghĩ đến anh. Em có cảm giác mình đang mơ, một giấc mơ dài vô tận. Bởi vì em yêu anh và chẳng bao giờ ngừng yêu anh.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh không lãng mạn để có thể làm thơ tặng em, anh không tài giỏi để có thể vẽ tranh tặng em, anh không giàu có để có thể mua cả vườn hồng tặng em…\n" +
                "Nhưng anh đủ hào phóng để có thể tặng em cả trái tim anh, đủ dũng cảm để có thể bảo vệ em suốt cuộc đời, đủ chung thủy để mãi yêu chỉ mình em trong đời…" +
                "Hãy nhận lấy trái tim anh, hãy để anh làm vệ sĩ của em suốt đời và hãy để … anh yêu em, em nhé!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Thời gian có thể trôi đi, mọi thứ có thể thay đổi nhưng tình yêu anh dành cho em sẽ mãi không bao giờ nhạt phai. Em thấy khó tin lắm đúng không? Vậy thì hãy để anh chứng minh cho em thấy nhé người anh yêu.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Ngày 14/2 luôn là ngày thật đặc biệt với anh và em. Vì đó lại thêm ngày anh được yêu thương chăm sóc em. Anh chúc em tràn đầy hạnh phúc ngọt ngào hãy gìn giữ và nuôi dưỡng tình yêu của chúng mình em nhé. Anh mong rằng Valentine gần đây nhất anh được nói với em rằng chúc bà xã của anh mọi sự tốt lành.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Em yêu! Chúng ta đã cùng vượt qua những khoảng thời gian khó khăn. Anh chỉ muốn cho tất cả mọi người biết rằng: em là duy nhất của anh… Anh yêu em rất nhiều!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "“Anh đã luôn mơ những giấc mơ thật đẹp, những giấc mơ trong đó có em…Em đến bên anh thật nồng nàn, say đắm như sự sắp đặt của số phận. Trong mơ, anh là người hạnh phúc, và anh cứ muốn kéo dài giấc mơ hạnh phúc ấy, kéo dài mãi mãi…Chúc em yêu một Valentine thật vui vẻ và hãy nhớ đến anh.”");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Valentine ấm áp và ngọt ngào em nhé. Hãy luôn ở bên cạnh anh, anh hứa sẽ nắm chặt tay em và đưa em đến cuối con đường. Love you so much!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Dậy đi bé, sáng rồi Valentine rồi kìa dậy đi bé ơi!, anh sai một thiên thần tới gọi bé dậy nhưng thiên thần bảo không thể đánh thức một thiên thần khác…Vì thế anh đành tự đánh thức bé vậy. Yêu Em thật nhiều!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Thỉnh thoảng anh trộm nghĩ, nếu một ngày anh không còn có em ở bên…. Và em biết không, anh luôn phải nhắm mắt, bịt tai, hét thật to, để ý nghĩ ấy không thể tiếp tục được nữa…. Vì em là lẽ sống của đời anh! Tình yêu của anh hãy thật hạnh phúc bên anh trong ngày Valentine nhé!");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Anh muốn nói rằng anh yêu và nhớ em rất nhiều… Nhưng anh hứa chúng ta sẽ cùng ăn mừng ngày này với thật nhiều tình yêu và hạnh phúc.");
        database.insert(TBL_NAME, null, values);
        values.put(COLUMN_CONTENT, "Em yêu! Đã có lần em nói với anh, anh là người đem đến cho em những niềm vui trong cuộc sống nhưng cũng đem đến cho em rất nhiều nỗi buồn. Nếu có một điều ước thì anh sẽ ước rằng mỗi ngày trôi qua đều là một ngày Valentine thật đẹp đối với tình yêu của chúng ta.");
        database.insert(TBL_NAME, null, values);
    }
}
