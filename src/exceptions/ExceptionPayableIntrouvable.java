package exceptions;
//
// TODO 08-- Ajoutez le code nécessaire pour créer l'exception générée
//         quand on essaie de référer è un payable inexistant
//
public class ExceptionPayableIntrouvable extends Exception {
    public ExceptionPayableIntrouvable(int ID) {
        super("Payable introuvable avec l'ID " + ID);
    }
}