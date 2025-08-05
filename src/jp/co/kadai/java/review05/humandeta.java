package jp.co.kadai.java.review05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class humandeta {

    public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
        Connection con =null;
        PreparedStatement pstmt =null;
        ResultSet rs =null;



        try {
        // 1.JDBCドライブのロード
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.DB接続
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost/kadaiab?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "24352435"
                );



        // 4. DBとやりとりする窓口（PreparedStatementオブジェクト）の作成
        String sql = "SELECT name, age FROM person WHERE id =?";
        pstmt = con.prepareStatement(sql);

        //5, 実行と結果を格納／代入
        System.out.print("検索キーワードを入力してください > ");
        String input =keyIn();
        int id =Integer.parseInt(input);

        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();

        //6. 結果の出力
        while(rs.next()) {
            //Nameの取得
            String name =rs.getString("name");
            // ageの取得
            int age =rs.getInt("age");

            //取得した値の表示
            System.out.println("名前："+name);
            System.out.println("年齢："+age);
        }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            // 7. 接続を閉じる
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("Statementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * 値をStringで返す
     */
    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {

        }
        return line;
    }





    }


