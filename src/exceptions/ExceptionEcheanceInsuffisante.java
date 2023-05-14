package exceptions;
//
// TODO 10-- Ajoutez le code nécessaire pour créer l'exception générée
//         quand le nombre de jours d'échéance passe sous zéro
//      Gracieusement fournie!
public class ExceptionEcheanceInsuffisante extends Exception
{
    public ExceptionEcheanceInsuffisante(int jours) {
        super("Enlever " + jours + " d'échéance nous amènera à une échéance négative.");
    }
}
