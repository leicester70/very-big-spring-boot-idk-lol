import java.util.HashMap;

public class test {
  public static void main(String[] args) {
    System.out.println(canConstruct("abc1233", "abc123"));
  }

  static boolean canConstruct(String ransomNote, String magazine) {
    HashMap<Character, Integer> hashbrown = new HashMap<Character, Integer>();
    for (char c : ransomNote.toCharArray()) {
      if (hashbrown.containsKey(c)) {
        hashbrown.replace(c, hashbrown.get(c), hashbrown.get(c) + 1);
      } else {
        hashbrown.put(c, 1);
      }
    }
    for (char c : magazine.toCharArray()) {
      if (hashbrown.containsKey(c) && hashbrown.get(c) > 1) {
        hashbrown.replace(c, hashbrown.get(c), hashbrown.get(c) - 1);
      } else {
        hashbrown.remove(c);
      }
    }
    return hashbrown.isEmpty();
  }

////  shittier but more idiot friendly version
//  static boolean canConstruct(String ransomNote, String magazine) {
//    char[] heyy = ransomNote.toCharArray();
//    HashMap<Character, Integer> hashbrown = new HashMap<Character, Integer>();
//    for (char c : heyy) {
//      try {
//        hashbrown.replace(c,hashbrown.get(c),hashbrown.get(c)+1);
//        System.out.println("found the char, added 1");
//      }
//      catch (Exception e) {
//        System.out.println(e.getMessage()+"\ncouldn't get that char, put new char into hashbrown (ofc init with a 1)");
//        hashbrown.put(c,1);
//      }
//    }
//    System.out.println(hashbrown.toString());
//    for (char c: magazine.toCharArray()) {
//      try {
//        if (hashbrown.containsKey(c) && hashbrown.get(c)>1){
//          System.out.println("digesting: "+c);
//          hashbrown.replace(c,hashbrown.get(c),hashbrown.get(c)-1);
//        } else {
//          hashbrown.remove(c);
//        }
//      }
//      catch (Exception e){
//        System.out.println(e.getMessage());
//      }
//    }
//    System.out.println(hashbrown);
//    return hashbrown.isEmpty();
//  }

}
