import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingGame {

    public int getBowlingScore(String bowlingCode) {
        int score = 0;
        int k = bowlingCode.indexOf("||");
        String sa = bowlingCode.substring(0, k);
        String se = bowlingCode.substring(k + 2);
        String[] arr = sa.split("\\|");
        List<String> fs = new ArrayList<String>(Arrays.asList(arr));
        int n = fs.size();
        if (!"".equals(se)) {
            fs.add(se);
        }
        fs.add("-");
        fs.add("-");//防止越界
        for (int i = 0; i < n; i++) {
            String f = fs.get(i);
            String[] farr = f.split("");
            if (farr.length == 1) {
                score += dealStrike(f, fs.get(i + 1), fs.get(i + 2));
            } else {
                score += dealOthers(f, fs.get(i + 1));
            }
        }
        return score;
    }

    /**
     * 处理Strike
     *
     * @param f
     * @param f1
     * @param f2
     * @return
     */
    private int dealStrike(String f, String f1, String f2) {
        int score = 0;
        if ("X".equals(f)) {
            score += 10;
            if ("X".equals(f1)) {
                score += 10;
                if ("X".equals(f2)) {
                    score += 10;
                } else {
                    score += dealCon1(f2);
                }
            } else {
                score += dealCon2(f1);//直接得到后面两球的分数和
            }
        }
        return score;
    }

    /**
     * 处理其他情况
     *
     * @param f
     * @param f1
     * @return
     */
    private int dealOthers(String f, String f1) {
        int score = 0;
        if (f.endsWith("/")) {//spare,-/,n/
            score += 10;
            score += dealCon1(f1);
        } else {//两次都没击倒完,有4种情况：nn,--,-n,n-
            score += dealCon2(f);
        }
        return score;
    }

    /**
     * 取下一个球数的分数
     *
     * @param f1
     * @return
     */
    private int dealCon1(String f1) {
        int cnt = 0;
        String s = f1.split("")[0];
        try {
            cnt = Integer.parseInt(s);
        } catch (Exception e) {
            if ("X".equals(s)) {
                cnt = 10;
            }
            //开头是-,miss
        }
        return cnt;
    }

    /**
     * 取后两个球数的分数
     *
     * @param f1
     * @return
     */
    private int dealCon2(String f1) {
        int score = 0;
        if (f1.endsWith("/")) {//spare,-/,n/
            score = 10;
        } else {//两次都没击倒完,有4种情况：nn,--,-n,n-
            score += dealCon1(f1.split("")[0]);
            score += dealCon1(f1.split("")[1]);
        }
        return score;
    }
}
