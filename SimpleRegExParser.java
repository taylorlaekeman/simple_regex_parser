class SimpleRegExParser {
    boolean[][] isMatch;
    boolean[][] isComputed;
    
    public boolean isMatch(String s, String p) {
        initializeArrays(s, p);
        return isSubMatch(s, p);
    }
    
    private void initializeArrays(String s, String p) {
        isMatch = new boolean[s.length() + 1][p.length() + 1];
        isComputed = new boolean[s.length() + 1][p.length() + 1];
    }
    
    private boolean isSubMatch(String s, String p) {                
        if (s.isEmpty() && p.isEmpty())
            return assignAndReturn(true, s, p);
        
        if (!s.isEmpty() && p.isEmpty())
            return assignAndReturn(false, s, p);
        
        if (isComputed(s, p))
            return isValid(s, p);
        
        boolean hasStar = hasStar(p);
                
        if (s.isEmpty()) {
            if (hasStar) {
                return assignAndReturn(isSubMatch(s, removeCharStar(p)), s, p);
            } else {
                return assignAndReturn(false, s, p);
            }
        }
        
        char sChar = s.charAt(0);
        char pChar = p.charAt(0);
        
        if (sChar != pChar && pChar != '.' && !hasStar)
            return assignAndReturn(false, s, p);
        
        if (sChar == pChar || pChar == '.') { // match
            if (hasStar) {
                return assignAndReturn(isSubMatch(removeChar(s), p) || isSubMatch(removeChar(s), removeCharStar(p)) || isSubMatch(s, removeCharStar(p)), s, p);
            } else {
                return assignAndReturn(isSubMatch(removeChar(s), removeChar(p)), s, p);
            }
        } else { // no match, hasStar is true
            return assignAndReturn(isSubMatch(s, removeCharStar(p)), s, p);
        }       
    }
    
    private boolean isComputed(String s, String p) {
        return isComputed[s.length()][p.length()];
    }
    
    private boolean isValid(String s, String p) {
        return isMatch[s.length()][p.length()];
    }
    
    private boolean assignAndReturn(boolean value, String s, String p) {
        isMatch[s.length()][p.length()] = value;
        isComputed[s.length()][p.length()] = true;
        return value;
    }
    
    private boolean hasStar(String p) {
        return p.length() > 1 && p.charAt(1) == '*';
    }
    
    private String removeChar(String s) {
        return s.substring(1);
    }
    
    private String removeCharStar(String p) {
        return p.substring(2);
    }
}
