package mybasics.myjpa.mylearningtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.relational.core.sql.In;

import java.util.*;

public class CoteRunning {

    @Test
    public void runTest() {
        int l = 5;
        int r = 521;
        List result = new ArrayList<Integer>();
        for(int x = l; x <= r; x++) {
            String s = Integer.toString(x);
            String[] p = s.split("");
            List list = Arrays.stream(p).toList();
            boolean isMatch = list.stream().allMatch((ele) -> {
                return "0".equals(ele) || "5".equals(ele);
            });
            if (isMatch) {
                result.add(x);
            }
        }
        System.out.println("result = " + result);
    }

    @Test
    public void runTest2() {
        int l = 5;
        int r = 521;
        List result = new ArrayList<Integer>();
        for(int x = l; x <= r; x++) {
            String s = Integer.toString(x);
            boolean isMatch = s.matches("[(0|5)]{1,}");
            if(isMatch) {
                result.add(x);
            }
        }
        System.out.println("result = " + result);
    }

    @Test
    public void test3() {
        int num = 3;
        int total = 12;
        int result = (2*total + num - num*num) / (2*num);
        System.out.println("result = " + result);
    }

    @Test
    public void testS() {
        String before = "hello";
        String target = "elloh";
        int result = -1;
        int len = before.length();
        for(int i = 1; i <= len; i++) {
            String after = before.substring(len-1).concat(before).substring(0,len);
            before = after;
            if(target.equals(after)) {
                result = i;
                break;
            }
        }
        System.out.println("result = " + result);
    }

    @Test
    public void pariTest() {
        int x = 1234;
        int y = 5678;
        String strX = Integer.toString(x);
        String strY = Integer.toString(y);
        ArrayList<String> splitX = new ArrayList<>(Arrays.asList(strX.split("")));
        ArrayList<String> splitY = new ArrayList<>(Arrays.asList(strY.split("")));
        ArrayList<String> pairStr = new ArrayList<>();
        for(int i = 0; i < splitX.size(); i++) {
            String cur = splitX.get(i);
            int targetIndex =  splitY.indexOf(cur);
            if(targetIndex > 0) {
                pairStr.add(cur);
                splitY.remove(targetIndex);
            }
        }
        int result = -1;
        if(pairStr.size() > 0) {
            pairStr.sort((o1, o2) -> { return Integer.valueOf(o2) - Integer.valueOf(o1); });
            String concated = "";
            for(String ele : pairStr) {
                concated = concated + ele;
            }
            result = Integer.valueOf(concated);
        }
        System.out.println("result = " + result);
    }


    @Test
    public void keyTest() {
        String[] keyMapList = {"ABACD", "BCEFD"};
        String[] targetList = {"ABCD", "AABB"};
        ArrayList<Integer> result = new ArrayList<>(); // [9,4]
        // targetList 반복문 돌리기
        for(int i = 0; i < targetList.length; i++) {
            // targetList 하나 꺼내옴 ex) "ABCD"
            String target = targetList[i];
            int total = 0;
            // target 분해함 ex) {"A", "B", "C", "D"}
            List<String> splited = Arrays.asList(target.split(""));
            // target 반복문 돌려서 하나씩 꺼내옴 ex) "A"
            for(String eleTarget : splited) {
                int targetIndex = -1;
                // "A" 뽑으려면 keyMap 중에서 최소값 뭐임? keyMap 하나씩 돌려보면서 찾음 ex) "ABACD"
                for(String keyMap : keyMapList) {
                    int keyIndex = keyMap.indexOf(eleTarget);
//                    if(keyIndex < 0 && targetIndex < 0) {
//                        continue;
//                    } else if(keyIndex < 0 && targetIndex > -1) {
//                        continue;
//                    } else if(keyIndex > -1 && targetIndex < 0) {
//                        targetIndex = keyIndex;
//                    } else if(keyIndex > -1 && targetIndex > -1) {
//                        if(keyIndex < targetIndex) {
//                            targetIndex = keyIndex;
//                        }
//                    }
                    if(keyIndex > -1) {
                        if(targetIndex < 0) {
                            targetIndex = keyIndex;
                        } else if(keyIndex < targetIndex) {
                            targetIndex = keyIndex;
                        }
                    }
                }
                // 한 글자라도 못 찾았으면 그 target 은 못 만드는 거임. target 분해한 거 반복문 취소
                if(targetIndex < 0) {
                    result.add(Integer.valueOf(-1));
                    break;
                } else {
                    // 최소값 찾았으면 넣어주기 (버튼 누르는 횟수는 index + 1)
                    total = total + targetIndex + 1;
                }
            }
            // 앞에서 못찾아서 -1 넣어준 거면 그냥 가고, 아직 안 넣어준 상태면 total 넣어주기
            if(result.size() < i +1) {
                result.add(Integer.valueOf(total));
            }
        }
        System.out.println("result = " + result);
    }


    @Test
    public void liningTest() {
        int personCnt = 3;
        int lineIndex = 5;
        ArrayList<Integer> result = new ArrayList<>();  // ex) [3, 1, 2]
    }


    @Test
    public void greedyTest() {
        String result = "";
        String numOrigin = "92615";
        int len = numOrigin.length();
        int removeLen = 2;
        int startNum = 0;
        int endNum = len - removeLen;
        ////////////////////////////////////
        List<String> orderList = extractOrder(startNum, len, endNum);
        List<String> extractList = new ArrayList<>();
        orderList.forEach((ele)->{
            String concat = "";
            List<String> orders = Arrays.asList(ele.split(""));
            for(String order : orders) {
                concat = concat + numOrigin.charAt(Integer.valueOf(order));
            }
            extractList.add(concat);
        });
        extractList.sort(Collections.reverseOrder());   // 여기서 select 해온 건 자릿수임.
        result = extractList.get(0);
        ////////////////////////////////////
        System.out.println("result = " + result);

    }

    private List<String> extractOrder(int startNum, int endNum, int depth) {
        // 뎁스 변경
        // 결과
        List<String> result = new ArrayList<>();
        // 가장 안쪽 뎁스
        if(depth - 1 < 1) {
            for (int i = startNum; i < endNum; i++) {
                result.add(Integer.toString(i));
            }
            return result;
        }
        // 안쪽이 아닌 뎁스
        for(int i = startNum; i < endNum; i++) {
            List<String> extractList = this.extractOrder(i+1, endNum, depth -1);
            for(int j = 0; j < extractList.size(); j++) {
                extractList.set(j, Integer.toString(i) + extractList.get(j));
            }
            result.addAll(extractList);
        }
        return result;
    }




    @Test
    public void greedyCopy() {
        //
        String number = "92615";
        int k = 2;
        //
        String answer = "";
        Stack<String> stack = new Stack();
        //
        for(int i=0; i<number.length(); i++) {
            String el = String.valueOf(number.charAt(i));
            while(k>0 && stack.size() -1 > 0 && Integer.valueOf(stack.get(stack.size() - 1)) < Integer.valueOf(el)) {
                stack.pop();
                k--;
            }
            stack.push(el);
        }
        List<String> sub = stack.subList(k, stack.size()-k);
        answer = String.join("", sub);
        System.out.println("answer = " + answer);

    }

    @Test
    public void numTest() {
        Map<String, Integer> numMap = new HashMap<>();
        String target = "123";
        numMap.put("zero", 0);
        numMap.put("one", 1);
        numMap.put("two", 2);
        numMap.put("three", 3);
        numMap.put("four", 4);
        numMap.put("five", 5);
        numMap.put("six", 6);
        numMap.put("seven", 7);
        numMap.put("eight", 8);
        numMap.put("nine", 9);
        Iterator<String> keyIter = numMap.keySet().iterator();
        while(keyIter.hasNext()) {
            String key = keyIter.next();
            target = target.replaceAll(key, Integer.toString(numMap.get(key)));

        }
        numMap.forEach((key, val) -> {
        });
        System.out.println("target = " + target);

    }

    @Test
    public void distanceTest() {
        String[][] places = {{"POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"},
                {"POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"},
                {"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"},
                {"OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"},
                {"PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP"}
        };
        int[] answer = new int[places.length];
        for(int i=0; i<places.length; i++) {
            String[] ele = places[i];
            List<List<String>> matrix = new ArrayList<>();
            for (int j=0; j < ele.length; j++) {
                matrix.add(Arrays.asList(ele[j].split("")));
            }
            int innerResult = 0;
            outerLoop:
            for (int y = 0; y < matrix.size() - 1; y++) {
                List<String> row = matrix.get(y);
                for (int x = 0; x < row.size() - 1; x++) {
                    List<String> target = Arrays.asList(matrix.get(y).get(x), matrix.get(y).get(x + 1), matrix.get(y + 1).get(x), matrix.get(y + 1).get(x + 1));
                    int countP = Collections.frequency(target, "P");
                    if (countP < 2) {
                        continue;
                    } else if (countP > 2) {
                        innerResult = 0;
                        break outerLoop;
                    } else if (countP == 2) {
                        boolean isAnyO = target.contains("O");
                        if ((!isAnyO) && (matrix.get(y).get(x).equals(matrix.get(y + 1).get(x + 1))) && (matrix.get(y).get(x + 1).equals(matrix.get(y+1).get(x)))) {
                            continue;
                        }  else {
                            innerResult = 0;
                            break outerLoop;
                        }
                    }
                }
                innerResult = 1;
            }
            answer[i] = innerResult;
            System.out.println("innerResult = " + innerResult);
            System.out.println();
        }
        System.out.println("result = " + answer);
    }

    @Test
    public void bfs() {
        boolean result = true;
//        String[] ele = {"POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"}; // true
//        String[] ele = {"POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"};   // false
//        String[] ele = {"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"};   // true
        String[] ele = {"OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"};   // true
        List<List<String>> square = new ArrayList<>();
        for (String str: ele) {
            square.add(Arrays.asList(str.split("")));
        }
        outerLoop: for(int rowIndex=0; rowIndex<square.size(); rowIndex++) {
            List<String> row = square.get(rowIndex);
            for(int colIndex=0; colIndex<row.size(); colIndex++) {
                boolean flag = checkPeople(square, rowIndex, colIndex, 2, new ArrayList<String>());
                if(!flag) {
                    result = false;
                    break outerLoop;
                }

            }
        }
        System.out.println("result = " + result);
    }
    
    private boolean checkPeople(List<List<String>> square, int rowIndex, int colIndex, int depth, List<String> visited) {
        // escape - visited
        String vertex = square.get(rowIndex).get(colIndex);
        String coordnate = colIndex+","+rowIndex;
        if(visited.contains(coordnate)) {
            return true;
        } else {
            visited.add(coordnate);
        }

        // escape - depth
        if(depth < 1) {
            return true;
        }

        // escape - result
        if("X".equals(vertex)) {
            return true;
        } else if(depth == 2) {
            if(!("P".equals(vertex))) {
                return true;
            }
        } else if("P".equals(vertex)) {
            return false;
        }

        // depth control
        depth--;

        // search
        boolean flag = true;
        if(rowIndex+1<square.size()) {
            if(flag) {
                flag = checkPeople(square, rowIndex+1, colIndex, depth, visited);
            }
        }
        if(rowIndex>0) {
            if(flag) {
                flag = checkPeople(square, rowIndex-1, colIndex, depth, visited);
            }
        }
        if(colIndex+1<square.get(rowIndex).size()) {
            if(flag) {
                flag = checkPeople(square, rowIndex, colIndex+1, depth, visited);
            }
        }
        if(colIndex>0) {
            if(flag) {
                flag = checkPeople(square, rowIndex, colIndex-1, depth, visited);
            }
        }
        return flag;
    }


    @Test
    public void searchTest() {
        List<List<String>> square = Arrays.asList(Arrays.asList("1, 1", "1, 2", "1, 3", "1, 4", "1, 5"),
                Arrays.asList("2, 1", "2, 2", "2, 3", "2, 4", "2, 5"),
                Arrays.asList("3, 1", "3, 2", "3, 3", "3, 4", "3, 5"),
                Arrays.asList("4, 1", "4, 2", "4, 3", "4, 4", "4, 5"),
                Arrays.asList("5, 1", "5, 2", "5, 3", "5, 4", "5, 5"));
        for(int rowIndex=0; rowIndex<square.size(); rowIndex++) {
            List<String> row = square.get(rowIndex);
            for(int colIndex=0; colIndex<row.size(); colIndex++) {
                boolean result = checkPeople(square, rowIndex, colIndex, 2, new ArrayList<String>());
                System.out.println("########################################" + result);
            }
        }

    }

    @Test
    public void dfsWhile() {
        String[] ele = {"POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"}; // true
        List<List<String>> square = new ArrayList<>();
        for (String str: ele) {
            square.add(Arrays.asList(str.split("")));
        }




        for(int rowIndex=0; rowIndex<square.size(); rowIndex++) {
            List<String> row = square.get(rowIndex);
            for(int colIndex=0; colIndex<row.size(); colIndex++) {
                List<String> visited = new ArrayList<>();
                List<String> toVisit = new ArrayList<>();
                // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                String coordinate = colIndex+","+rowIndex;
                toVisit.add(coordinate);
                while(toVisit.size() > 0) {
                    String target = toVisit.get(0);
                    if(visited.contains(target)) {
                        toVisit.remove(0);
                        continue;
                    }
                    System.out.println("target = " + target);
                    // 여기에 depth 개념 추가 + 로직 추가
                    String[] splited = target.split(",");
                    int targetCol = Integer.valueOf(splited[0]);
                    int targetRow = Integer.valueOf(splited[1]);
                    if(targetRow>0) {
                        toVisit.add(targetCol+","+(targetRow-1));
                    }
                    if(targetRow+1<square.size()) {
                        toVisit.add(targetCol+","+(targetRow+1));
                    }
                    if(targetCol>0) {
                        toVisit.add((targetCol-1)+","+targetRow);
                    }
                    if(targetCol+1<square.get(targetRow).size()) {
                        toVisit.add((targetCol+1)+","+targetRow);
                    }
                    toVisit.remove(0);
                    visited.add(target);

                }
                // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            }
        }

    }


    @Test
    public void arrTest() {
        List<String> visited = new ArrayList<>();
        visited.add("1");
        visited.add("2");
        visited.add("3");
        visited.add("4");
        System.out.println("visited.indexOf('4') = " + visited.indexOf("4"));
        visited.remove(0);
        System.out.println("visited.indexOf('4') = " + visited.indexOf("4"));

    }

}




