import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Player {
    private String name;
    private int color; // 0 pour blanc, 1 pour noir
    public Player(String name, int color) {
        this.name = name;
        this.color = color;
    }
    public String getName() {
        return name;
    }
    public int getColor() {
        return color;
    }
}
class Position {
    private char column;
    private int row;
    public Position(char column, int row) {
        this.column = column;
        this.row = row;
    }
    public String toString() {
        String S=column + String.valueOf(row);
        return column + String.valueOf(row);
    }
    public char getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }
}

class King {
    private Position position;
    private int color; // 0 pour blanc, 1 pour noir
    public King(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "K";  // Représentation du Roi
    }
    public Position getPosition() {
        return position;
    }
    public int getColor() {
        return color;
    }
    public boolean isValidMove(Position newPosition, Cell[][] board) {
        int startCol = position.getColumn() - 'a'; // Conversion en index
        int startRow = 8 - position.getRow(); // Conversion en index inverse
        int endCol = newPosition.getColumn() - 'a'; // Conversion en index
        int endRow = 8 - newPosition.getRow(); // Conversion en index inverse
        if (startCol == endCol && startRow == endRow) {
            return false;
        }
        if (Math.abs(startCol - endCol) <= 1 && Math.abs(startRow - endRow) <= 1) {// Vérifie que le mouvement est d'une case dans toutes les directions
            if (board[endRow][endCol].isEmpty() || board[endRow][endCol].getPlayer().getColor() != this.color) {// Verifie si la case de destination est vide ou occupée par une pièce adverse
                return true;
            }
        }
        return false;
    }
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }
}

class Queen {
    private Position position;
    private int color; // 0 pour blanc, 1 pour noir
    public Queen(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "Q";  // Représentation de la Reine
    }
    public Position getPosition() {
        return position;
    }
    public int getColor() {
        return color;
    }
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }
    public boolean isValidMove(Position newPosition, Cell[][] board) {
        int startCol = position.getColumn() - 'a'; // Conversion en index
        int startRow = 8 - position.getRow(); // Conversion en index inverse
        int endCol = newPosition.getColumn() - 'a'; // Conversion en index
        int endRow = 8 - newPosition.getRow(); // Conversion en index inverse
        // Vérifie que la case cible n'est pas la même que la case de départ
        if (startCol == endCol && startRow == endRow) {
            return false;  // La Reine ne doit pas "rester" sur place
        }
        // Vérification des mouvements autorisés : horizontal, vertical ou diagonal
        boolean isStraightMove = (startCol == endCol || startRow == endRow);
        boolean isDiagonalMove = (Math.abs(startCol - endCol) == Math.abs(startRow - endRow));
        if (isStraightMove || isDiagonalMove) {
            return isPathClear(startCol, startRow, endCol, endRow, board);
        }
        return false;  // Si ce n'est ni un mouvement en ligne droite ni un diagonal, ce n'est pas valide
    }
    private boolean isPathClear(int startCol, int startRow, int endCol, int endRow, Cell[][] board) {
        int colDirection = Integer.signum(endCol - startCol); // +1, -1 ou 0
        int rowDirection = Integer.signum(endRow - startRow); // +1, -1 ou 0
        int currentCol = startCol + colDirection;
        int currentRow = startRow + rowDirection;
        // On avance d'une case à la fois jusqu'à la case d'arrivée (sans l'inclure)
        while (currentCol != endCol || currentRow != endRow) {
            if (!board[currentRow][currentCol].isEmpty()) {
                return false; // Si une pièce bloque le passage, mouvement impossible
            }
            currentCol += colDirection;
            currentRow += rowDirection;
        }
        // Vérifie la case de destination : doit être vide ou contenir une pièce adverse
        return board[endRow][endCol].isEmpty() || board[endRow][endCol].getPlayer().getColor() != this.color;
    }
}


class Bishop {
    private Position position;
    private int color;
    public Bishop(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "B";  // Représentation du Fou
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position newPosition) {
        this.position = newPosition;  // Met à jour la position de la pièce
    }
    public int getColor() {
        return color;
    }
    public boolean isValidMove(Position newPosition, Cell[][] board) {
        // Déplacement en diagonale seulement
        int startCol = position.getColumn() - 'a';
        int startRow = 8 - position.getRow();
        int endCol = newPosition.getColumn() - 'a';
        int endRow = 8 - newPosition.getRow();
        // Vérifier que le mouvement est bien en diagonale (même différence entre les colonnes et les lignes)
        if (Math.abs(startCol - endCol) != Math.abs(startRow - endRow)) {
            return false;
        }
        // Calculer les étapes à suivre en diagonale
        int colDirection = (endCol > startCol) ? 1 : -1;  // Direction de la colonne (gauche à droite ou droite à gauche)
        int rowDirection = (endRow > startRow) ? 1 : -1;  // Direction de la ligne (haut en bas ou bas en haut)
        // Vérifier si le chemin est libre, on vérifie toutes les cases entre le départ et l'arrivée
        int currentCol = startCol + colDirection;
        int currentRow = startRow + rowDirection;
        while (currentCol != endCol && currentRow != endRow) {
            // Si une pièce est présente sur une des cases intermédiaires, le mouvement est invalide
            if (!board[8 - currentRow][currentCol].isEmpty()) {
                return false;  // Il y a une pièce qui bloque le mouvement
            }
            currentCol += colDirection;
            currentRow += rowDirection;
        }
        return true;  // Si le chemin est libre et que le mouvement est en diagonale, c'est valide
    }
}

class Rook {
    private Position position;
    private int color;
    public Rook(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "R";  // Représentation de la Tour
    }
    public Position getPosition() {
        return position;
    }
    public int getColor() {
        return color;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isValidMove(Position newPosition, Cell[][] board) {
        int startCol = position.getColumn() - 'a';
        int startRow = 8 - position.getRow();
        int endCol = newPosition.getColumn() - 'a';
        int endRow = 8 - newPosition.getRow();
        // Vérification que le déplacement est uniquement horizontal ou vertical
        if (startCol != endCol && startRow != endRow) {
            return false;
        }
        // Vérification que le chemin est libre
        if (!isPathClear(startCol, startRow, endCol, endRow, board)) {
            return false;
        }
        // Vérification si la case de destination est vide ou occupée par une pièce adverse
        return board[endRow][endCol].isEmpty() || board[endRow][endCol].getPlayer().getColor() != this.color;
    }
    private boolean isPathClear(int startCol, int startRow, int endCol, int endRow, Cell[][] board) {
        int colDirection = Integer.signum(endCol - startCol); // +1, -1 ou 0
        int rowDirection = Integer.signum(endRow - startRow); // +1, -1 ou 0
        int currentCol = startCol + colDirection;
        int currentRow = startRow + rowDirection;
        // On avance d'une case à la fois jusqu'à la case d'arrivée (sans l'inclure)
        while (currentCol != endCol || currentRow != endRow) {
            if (!board[currentRow][currentCol].isEmpty()) {
                return false; // Une pièce bloque le passage
            }
            currentCol += colDirection;
            currentRow += rowDirection;
        }
        return true;
    }
}

class Knight {
    private Position position;
    private int color;
    public Knight(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "N";  // Représentation du Cavalier
    }
    public Position getPosition() {
        return position;
    }
    public int getColor() {
        return color;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isValidMove(Position newPosition, Cell[][] board) {
        int startCol = position.getColumn() - 'a';
        int startRow = 8 - position.getRow();
        int endCol = newPosition.getColumn() - 'a';
        int endRow = 8 - newPosition.getRow();
        // Vérifie que le mouvement est bien en "L"
        boolean isLMove = (Math.abs(startCol - endCol) == 2 && Math.abs(startRow - endRow) == 1) ||
                (Math.abs(startCol - endCol) == 1 && Math.abs(startRow - endRow) == 2);
        if (!isLMove) {
            return false; // Pas un mouvement valide
        }
        // Vérifie si la case de destination est vide ou contient une pièce ennemie
        return board[endRow][endCol].isEmpty() || board[endRow][endCol].getPlayer().getColor() != this.color;
    }
}

class Pawn {
    private Position position;
    private int color; // 0 pour Blanc, 1 pour Noir
    public Pawn(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "P";  // Représentation du Pion
    }
    public Position getPosition() {
        return position;
    }
    public int getColor() {
        return color;
    }
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }
    public boolean isValidMove(Position newPosition, Cell[][] board) {
        int startCol = position.getColumn() - 'a';
        int startRow = 8 - position.getRow();
        int endCol = newPosition.getColumn() - 'a';
        int endRow = 8 - newPosition.getRow();
        int direction = (color == 0) ? -1 : 1; // Blancs avancent vers le haut (-1), Noirs vers le bas (+1)
        int startRowInitial = (color == 0) ? 6 : 1; // Ligne de départ des pions
        // Déplacement d'une case vers l'avant
        if (startCol == endCol && startRow + direction == endRow) {
            return board[endRow][endCol].isEmpty();
        }
        // Déplacement de deux cases si c'est le premier mouvement
        if (startCol == endCol && startRow == startRowInitial && startRow + 2 * direction == endRow) {
            return board[startRow + direction][endCol].isEmpty() && board[endRow][endCol].isEmpty();
        }
        // Capture en diagonale
        if (Math.abs(startCol - endCol) == 1 && startRow + direction == endRow) {
            return !board[endRow][endCol].isEmpty() && board[endRow][endCol].getPlayer().getColor() != this.color;
        }
        return false;
    }
}

class Cell {
    private final Position position;  // Position de la cellule
    private Object piece;  // Référence à une pièce (peut être n'importe quelle classe de pièce)
    private Player player; // Joueur qui possède la pièce
    public Cell(Position position) {
        this.position = position;
        this.piece = null;  // Par défaut, la cellule est vide
        this.player = null;
    }
    public boolean isEmpty() {
        return piece == null;
    }
    public void placePiece(Object piece, Player player) {
        this.piece = piece;
        this.player = player; // Associe la pièce au joueur
    }
    public void removePiece() {
        this.piece = null;
        this.player = null;  // La cellule redevient vide, plus de joueur associé
    }
    public Object getPiece() {
        return piece;
    }
    public Player getPlayer() {
        return player;
    }
    public Position getPosition() {
        return position;
    }
    public String displayPiece() {
        return (piece != null) ? piece.toString() : " ";  // Affiche la pièce ou un espace si vide
    }
}

class Chess {
    private Cell[][] board;
    private Player[] players;
    private Player currentPlayer;
    private List<Object> whitePieces;  // Liste des pièces du joueur blanc
    private List<Object> blackPieces;  // Liste des pièces du joueur noir
    public void play() {
        while (true) {
            createPlayers();       // Demander aux joueurs de saisir leur nom
            initialiseBoard();      // Initialiser l'échiquier
            while (!isCheckMate()) {  // Tant qu'il n'y a pas de CheckMate
                printBoard();       // Afficher l'état actuel de l'échiquier
                String move;
                do {
                    move = askMove();  // Demander le coup au joueur
                } while (!isValidMove(move)); // Répéter si le coup est invalide
                updateBoard(move);    // Mettre à jour l'échiquier avec le nouveau coup
                switchPlayer();       // Changer de joueur
            }
        }
    }
    private void createPlayers() {
        Scanner scanner = new Scanner(System.in);
        players = new Player[2];
        System.out.println("Entrez le nom du joueur 1 (blanc): ");
        players[0] = new Player(scanner.nextLine(), 0); // Joueur blanc
        System.out.println("Entrez le nom du joueur 2 (noir): ");
        players[1] = new Player(scanner.nextLine(), 1); // Joueur noir
        currentPlayer = players[0]; // Le joueur blanc commence
    }
    private void initialiseBoard() {
        board = new Cell[8][8];
        // Initialiser toutes les cellules de l'échiquier
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Cell(new Position((char) ('a' + j), 8 - i));
            }
        }
        // Supposons que tu as deux joueurs, "player1" et "player2"
        Player player1 = new Player("Player 1", 0);  // Blanc
        Player player2 = new Player("Player 2", 1);  // Noir
        // Création des listes de pièces pour chaque joueur
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        // Placement des pièces blanches (ligne 1 et 2)
        Rook whiteRook1 = new Rook(new Position('a', 1), 0);
        Rook whiteRook2 = new Rook(new Position('h', 1), 0);
        whitePieces.add(whiteRook1);
        whitePieces.add(whiteRook2);
        board[0][0].placePiece(whiteRook1, player1);
        board[0][7].placePiece(whiteRook2, player1);
        Knight whiteKnight1 = new Knight(new Position('b', 1), 0);
        Knight whiteKnight2 = new Knight(new Position('g', 1), 0);
        whitePieces.add(whiteKnight1);
        whitePieces.add(whiteKnight2);
        board[0][1].placePiece(whiteKnight1, player1);
        board[0][6].placePiece(whiteKnight2, player1);
        Bishop whiteBishop1 = new Bishop(new Position('c', 1), 0);
        Bishop whiteBishop2 = new Bishop(new Position('f', 1), 0);
        whitePieces.add(whiteBishop1);
        whitePieces.add(whiteBishop2);
        board[0][2].placePiece(whiteBishop1, player1);
        board[0][5].placePiece(whiteBishop2, player1);
        Queen whiteQueen = new Queen(new Position('d', 1), 0);
        King whiteKing = new King(new Position('e', 1), 0);
        whitePieces.add(whiteQueen);
        whitePieces.add(whiteKing);
        board[0][3].placePiece(whiteQueen, player1);
        board[0][4].placePiece(whiteKing, player1);
        // Placement des pions blancs (ligne 2)
        for (int j = 0; j < 8; j++) {
            Pawn whitePawn = new Pawn(new Position((char) ('a' + j), 2), 0);
            whitePieces.add(whitePawn);
            board[1][j].placePiece(whitePawn, player1);
        }
        // Placement des pièces noires (ligne 8 et 7)
        Rook blackRook1 = new Rook(new Position('a', 8), 1);
        Rook blackRook2 = new Rook(new Position('h', 8), 1);
        blackPieces.add(blackRook1);
        blackPieces.add(blackRook2);
        board[7][0].placePiece(blackRook1, player2);
        board[7][7].placePiece(blackRook2, player2);
        Knight blackKnight1 = new Knight(new Position('b', 8), 1);
        Knight blackKnight2 = new Knight(new Position('g', 8), 1);
        blackPieces.add(blackKnight1);
        blackPieces.add(blackKnight2);
        board[7][1].placePiece(blackKnight1, player2);
        board[7][6].placePiece(blackKnight2, player2);
        Bishop blackBishop1 = new Bishop(new Position('c', 8), 1);
        Bishop blackBishop2 = new Bishop(new Position('f', 8), 1);
        blackPieces.add(blackBishop1);
        blackPieces.add(blackBishop2);
        board[7][2].placePiece(blackBishop1, player2);
        board[7][5].placePiece(blackBishop2, player2);
        Queen blackQueen = new Queen(new Position('d', 8), 1);
        King blackKing = new King(new Position('e', 8), 1);
        blackPieces.add(blackQueen);
        blackPieces.add(blackKing);
        board[7][3].placePiece(blackQueen, player2);
        board[7][4].placePiece(blackKing, player2);
        // Placement des pions noirs (ligne 7)
        for (int j = 0; j < 8; j++) {
            Pawn blackPawn = new Pawn(new Position((char) ('a' + j), 7), 1);
            blackPieces.add(blackPawn);
            board[6][j].placePiece(blackPawn, player2);
        }
        // Les pièces sont stockées dans les listes locales ici, donc on ne les associe plus aux joueurs via `setPieces`.
    }

    private void printBoard() {
        System.out.println("  a b c d e f g h"); // Afficher les lettres des colonnes
        for (int i = 7; i >= 0; i--) { // Inverser le sens pour afficher la ligne 8 en bas
            System.out.print((i + 1) + " "); // Afficher les numéros des lignes
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j].displayPiece() + " ");
            }
            System.out.println(" " + (i + 1)); // Afficher le numéro à droite aussi
        }
        System.out.println("  a b c d e f g h"); // Répéter en bas pour symétrie
    }

    private String askMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(currentPlayer.getName() + ", entrez votre coup (ex : Nb1 c3): ");
        return scanner.nextLine();
    }
    private Object findPieceByType(List<Object> pieces, String pieceType, Position position) {
        for (Object piece : pieces) {
            if (piece instanceof Rook && pieceType.equals("R")) {
                Rook rook = (Rook) piece;
                if (rook.getPosition().toString().equals(position.toString())) {
                    return rook;
                }
            } else if (piece instanceof Knight && pieceType.equals("N")) {
                Knight knight = (Knight) piece;
                if (knight.getPosition().toString().equals(position.toString())) {
                    return knight;
                }
            } else if (piece instanceof Bishop && pieceType.equals("B")) {
                Bishop bishop = (Bishop) piece;
                if (bishop.getPosition().toString().equals(position.toString())) {
                    return bishop;
                }
            } else if (piece instanceof Queen && pieceType.equals("Q")) {
                Queen queen = (Queen) piece;
                if (queen.getPosition().toString().equals(position.toString())) {
                    return queen;
                }
            } else if (piece instanceof King && pieceType.equals("K")) {
                King king = (King) piece;
                if (king.getPosition().toString().equals(position.toString())) {
                    return king;
                }
            } else if (piece instanceof Pawn && pieceType.equals("P")) {
                Pawn pawn = (Pawn) piece;
                if (pawn.getPosition().toString().equals(position.toString())) {
                    return pawn;
                }
            }
        }
        return null;  // Aucune pièce trouvée
    }

    private boolean isValidMove(String move) {
        // Vérifier le format de base : une pièce, une case de départ, une case d'arrivée
        String regex;
        regex = "[KQRBNP]([a-h][1-8]) ([a-h][1-8])";
        if (!move.matches(regex)) {
            System.out.println("Erreur : Le format du coup est incorrect. Utilisez le format suivant : Nb1 c3");
            return false; // Si le format est incorrect
        }
        // Extraire la pièce, la position de départ et la position d'arrivée
        char pieceChar = move.charAt(0);  // Premier caractère (K, Q, R, etc.)
        char startCol = move.charAt(1);  // Colonne de départ (ex : 'a')
        char startRowChar = move.charAt(2);  // Rangée de départ (ex : '1')
        int startRow = startRowChar - '0';    // Convertir '1' en 1, '2' en 2, etc.
        char endCol = move.charAt(4);
        char endRowChar = move.charAt(5);
        int endRow = endRowChar - '0';

        // Convertir les coordonnées en position sur l'échiquier
        Position start = new Position(startCol, startRow);
        Position end = new Position(endCol, endRow);
        // Rechercher la pièce dans la liste des pièces du joueur courant (whitePieces ou blackPieces)
        List<Object> pieces = (currentPlayer.getColor() == 0) ? whitePieces : blackPieces;
        // Utiliser findPieceByType pour trouver la pièce spécifique à cette position
        Object piece = findPieceByType(pieces, String.valueOf(pieceChar), start);
        if (piece == null) {
            return false; // Pas de pièce trouvée à la position de départ
        }
        // Vérifier si la pièce peut effectuer le mouvement en utilisant sa propre méthode isValidMove
        if (piece instanceof Rook) {
            return ((Rook) piece).isValidMove(end, board);
        } else if (piece instanceof Knight) {
            return ((Knight) piece).isValidMove(end, board);
        } else if (piece instanceof Bishop) {
            return ((Bishop) piece).isValidMove(end, board);
        } else if (piece instanceof Queen) {
            return ((Queen) piece).isValidMove(end, board);
        } else if (piece instanceof King) {
            return ((King) piece).isValidMove(end, board);
        } else if (piece instanceof Pawn) {
            return ((Pawn) piece).isValidMove(end, board);
        }
        return false; // Si le mouvement n'est pas valide pour la pièce
    }

    private void updateBoard(String move) {
        // Extraire les informations du mouvement : type de pièce, position de départ et position d'arrivée
        char pieceChar = move.charAt(0);  // Premier caractère
        char startCol = move.charAt(1);  // Colonne de départ
        char startRowChar = move.charAt(2);  // Rangée de départ
        int startRow = startRowChar - '0';
        int startColInt= startCol - 'a' +1;
        char endCol = move.charAt(4);    // Colonne d'arrivée
        char endRowChar = move.charAt(5);    // Rangée d'arrivée
        int endRow = endRowChar - '0';  // Convertir '3' en 3, '4'
        int endColInt= endCol- 'a' +1;
        // Convertir les coordonnées en position sur l'échiquier
        Position start = new Position(startCol, startRow);
        Position end = new Position(endCol, endRow);
        // Rechercher la pièce dans la liste des pièces du joueur courant
        List<Object> pieces = (currentPlayer.getColor() == 0) ? whitePieces : blackPieces;
        // Utiliser findPieceByType pour trouver la pièce spécifique à cette position
        Object piece = findPieceByType(pieces, String.valueOf(pieceChar), start);
        if (piece == null) {
            System.out.println("Aucune pièce trouvée à la position de départ");
            return; // Sortir si aucune pièce n'est trouvée à la position de départ
        }
        // Mettre à jour la position de la pièce sur le plateau
        //On convertie le Col en nombre
        Cell startCell = board[startRow-1][startColInt-1];
        Cell endCell = board[endRow-1][endColInt-1];
        // Supprimer la pièce de la case de départ
        startCell.removePiece();
        // Placer la pièce à la nouvelle position
        endCell.placePiece(piece, currentPlayer);  // Associer la pièce au joueur
        // Mettre à jour la position de la pièce
        if (piece instanceof Rook) {
            ((Rook) piece).setPosition(end);
        } else if (piece instanceof Knight) {
            ((Knight) piece).setPosition(end);
        } else if (piece instanceof Bishop) {
            ((Bishop) piece).setPosition(end);
        } else if (piece instanceof Queen) {
            ((Queen) piece).setPosition(end);
        } else if (piece instanceof King) {
            ((King) piece).setPosition(end);
        } else if (piece instanceof Pawn) {
            ((Pawn) piece).setPosition(end);
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == players[0]) ? players[1] : players[0];
    }
    private boolean isCheckMate() {
        return false;
    }
}
public class Main {
    public static void main(String[] args) {
        Chess chess = new Chess();
        chess.play();
    }
}
 
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Player {
    private String name;
    private int color; // 0 pour blanc, 1 pour noir
    public Player(String name, int color) {
        this.name = name;
        this.color = color;
    }
    public String getName() {
        return name;
    }
    public int getColor() {
        return color;
    }
}
class Position {
    private char column;
    private int row;
    public Position(char column, int row) {
        this.column = column;
        this.row = row;
    }
    public String toString() {
        String S=column + String.valueOf(row);
        return column + String.valueOf(row);
    }
    public char getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }
}

class King {
    private Position position;
    private int color; // 0 pour blanc, 1 pour noir
    public King(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "K";  // Représentation du Roi
    }
    public Position getPosition() {
        return position;
    }
    public int getColor() {
        return color;
    }
    public boolean isValidMove(Position newPosition, Cell[][] board) {
        int startCol = position.getColumn() - 'a'; // Conversion en index
        int startRow = 8 - position.getRow(); // Conversion en index inverse
        int endCol = newPosition.getColumn() - 'a'; // Conversion en index
        int endRow = 8 - newPosition.getRow(); // Conversion en index inverse
        if (startCol == endCol && startRow == endRow) {
            return false;
        }
        if (Math.abs(startCol - endCol) <= 1 && Math.abs(startRow - endRow) <= 1) {// Vérifie que le mouvement est d'une case dans toutes les directions
            if (board[endRow][endCol].isEmpty() || board[endRow][endCol].getPlayer().getColor() != this.color) {// Verifie si la case de destination est vide ou occupée par une pièce adverse
                return true;
            }
        }
        return false;
    }
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }
}

class Queen {
    private Position position;
    private int color; // 0 pour blanc, 1 pour noir
    public Queen(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "Q";  // Représentation de la Reine
    }
    public Position getPosition() {
        return position;
    }
    public int getColor() {
        return color;
    }
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }
    public boolean isValidMove(Position newPosition, Cell[][] board) {
        int startCol = position.getColumn() - 'a'; // Conversion en index
        int startRow = 8 - position.getRow(); // Conversion en index inverse
        int endCol = newPosition.getColumn() - 'a'; // Conversion en index
        int endRow = 8 - newPosition.getRow(); // Conversion en index inverse
        // Vérifie que la case cible n'est pas la même que la case de départ
        if (startCol == endCol && startRow == endRow) {
            return false;  // La Reine ne doit pas "rester" sur place
        }
        // Vérification des mouvements autorisés : horizontal, vertical ou diagonal
        boolean isStraightMove = (startCol == endCol || startRow == endRow);
        boolean isDiagonalMove = (Math.abs(startCol - endCol) == Math.abs(startRow - endRow));
        if (isStraightMove || isDiagonalMove) {
            return isPathClear(startCol, startRow, endCol, endRow, board);
        }
        return false;  // Si ce n'est ni un mouvement en ligne droite ni un diagonal, ce n'est pas valide
    }
    private boolean isPathClear(int startCol, int startRow, int endCol, int endRow, Cell[][] board) {
        int colDirection = Integer.signum(endCol - startCol); // +1, -1 ou 0
        int rowDirection = Integer.signum(endRow - startRow); // +1, -1 ou 0
        int currentCol = startCol + colDirection;
        int currentRow = startRow + rowDirection;
        // On avance d'une case à la fois jusqu'à la case d'arrivée (sans l'inclure)
        while (currentCol != endCol || currentRow != endRow) {
            if (!board[currentRow][currentCol].isEmpty()) {
                return false; // Si une pièce bloque le passage, mouvement impossible
            }
            currentCol += colDirection;
            currentRow += rowDirection;
        }
        // Vérifie la case de destination : doit être vide ou contenir une pièce adverse
        return board[endRow][endCol].isEmpty() || board[endRow][endCol].getPlayer().getColor() != this.color;
    }
}


class Bishop {
    private Position position;
    private int color;
    public Bishop(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "B";  // Représentation du Fou
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position newPosition) {
        this.position = newPosition;  // Met à jour la position de la pièce
    }
    public int getColor() {
        return color;
    }
    public boolean isValidMove(Position newPosition, Cell[][] board) {
        // Déplacement en diagonale seulement
        int startCol = position.getColumn() - 'a';
        int startRow = 8 - position.getRow();
        int endCol = newPosition.getColumn() - 'a';
        int endRow = 8 - newPosition.getRow();
        // Vérifier que le mouvement est bien en diagonale (même différence entre les colonnes et les lignes)
        if (Math.abs(startCol - endCol) != Math.abs(startRow - endRow)) {
            return false;
        }
        // Calculer les étapes à suivre en diagonale
        int colDirection = (endCol > startCol) ? 1 : -1;  // Direction de la colonne (gauche à droite ou droite à gauche)
        int rowDirection = (endRow > startRow) ? 1 : -1;  // Direction de la ligne (haut en bas ou bas en haut)
        // Vérifier si le chemin est libre, on vérifie toutes les cases entre le départ et l'arrivée
        int currentCol = startCol + colDirection;
        int currentRow = startRow + rowDirection;
        while (currentCol != endCol && currentRow != endRow) {
            // Si une pièce est présente sur une des cases intermédiaires, le mouvement est invalide
            if (!board[8 - currentRow][currentCol].isEmpty()) {
                return false;  // Il y a une pièce qui bloque le mouvement
            }
            currentCol += colDirection;
            currentRow += rowDirection;
        }
        return true;  // Si le chemin est libre et que le mouvement est en diagonale, c'est valide
    }
}

class Rook {
    private Position position;
    private int color;
    public Rook(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "R";  // Représentation de la Tour
    }
    public Position getPosition() {
        return position;
    }
    public int getColor() {
        return color;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isValidMove(Position newPosition, Cell[][] board) {
        int startCol = position.getColumn() - 'a';
        int startRow = 8 - position.getRow();
        int endCol = newPosition.getColumn() - 'a';
        int endRow = 8 - newPosition.getRow();
        // Vérification que le déplacement est uniquement horizontal ou vertical
        if (startCol != endCol && startRow != endRow) {
            return false;
        }
        // Vérification que le chemin est libre
        if (!isPathClear(startCol, startRow, endCol, endRow, board)) {
            return false;
        }
        // Vérification si la case de destination est vide ou occupée par une pièce adverse
        return board[endRow][endCol].isEmpty() || board[endRow][endCol].getPlayer().getColor() != this.color;
    }
    private boolean isPathClear(int startCol, int startRow, int endCol, int endRow, Cell[][] board) {
        int colDirection = Integer.signum(endCol - startCol); // +1, -1 ou 0
        int rowDirection = Integer.signum(endRow - startRow); // +1, -1 ou 0
        int currentCol = startCol + colDirection;
        int currentRow = startRow + rowDirection;
        // On avance d'une case à la fois jusqu'à la case d'arrivée (sans l'inclure)
        while (currentCol != endCol || currentRow != endRow) {
            if (!board[currentRow][currentCol].isEmpty()) {
                return false; // Une pièce bloque le passage
            }
            currentCol += colDirection;
            currentRow += rowDirection;
        }
        return true;
    }
}

class Knight {
    private Position position;
    private int color;
    public Knight(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "N";  // Représentation du Cavalier
    }
    public Position getPosition() {
        return position;
    }
    public int getColor() {
        return color;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isValidMove(Position newPosition, Cell[][] board) {
        int startCol = position.getColumn() - 'a';
        int startRow = 8 - position.getRow();
        int endCol = newPosition.getColumn() - 'a';
        int endRow = 8 - newPosition.getRow();
        // Vérifie que le mouvement est bien en "L"
        boolean isLMove = (Math.abs(startCol - endCol) == 2 && Math.abs(startRow - endRow) == 1) ||
                (Math.abs(startCol - endCol) == 1 && Math.abs(startRow - endRow) == 2);
        if (!isLMove) {
            return false; // Pas un mouvement valide
        }
        // Vérifie si la case de destination est vide ou contient une pièce ennemie
        return board[endRow][endCol].isEmpty() || board[endRow][endCol].getPlayer().getColor() != this.color;
    }
}

class Pawn {
    private Position position;
    private int color; // 0 pour Blanc, 1 pour Noir
    public Pawn(Position position, int color) {
        this.position = position;
        this.color = color;
    }
    public String toString() {
        return "P";  // Représentation du Pion
    }
    public Position getPosition() {
        return position;
    }
    public int getColor() {
        return color;
    }
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }
    public boolean isValidMove(Position newPosition, Cell[][] board) {
        int startCol = position.getColumn() - 'a';
        int startRow = 8 - position.getRow();
        int endCol = newPosition.getColumn() - 'a';
        int endRow = 8 - newPosition.getRow();
        int direction = (color == 0) ? -1 : 1; // Blancs avancent vers le haut (-1), Noirs vers le bas (+1)
        int startRowInitial = (color == 0) ? 6 : 1; // Ligne de départ des pions
        // Déplacement d'une case vers l'avant
        if (startCol == endCol && startRow + direction == endRow) {
            return board[endRow][endCol].isEmpty();
        }
        // Déplacement de deux cases si c'est le premier mouvement
        if (startCol == endCol && startRow == startRowInitial && startRow + 2 * direction == endRow) {
            return board[startRow + direction][endCol].isEmpty() && board[endRow][endCol].isEmpty();
        }
        // Capture en diagonale
        if (Math.abs(startCol - endCol) == 1 && startRow + direction == endRow) {
            return !board[endRow][endCol].isEmpty() && board[endRow][endCol].getPlayer().getColor() != this.color;
        }
        return false;
    }
}

class Cell {
    private final Position position;  // Position de la cellule
    private Object piece;  // Référence à une pièce (peut être n'importe quelle classe de pièce)
    private Player player; // Joueur qui possède la pièce
    public Cell(Position position) {
        this.position = position;
        this.piece = null;  // Par défaut, la cellule est vide
        this.player = null;
    }
    public boolean isEmpty() {
        return piece == null;
    }
    public void placePiece(Object piece, Player player) {
        this.piece = piece;
        this.player = player; // Associe la pièce au joueur
    }
    public void removePiece() {
        this.piece = null;
        this.player = null;  // La cellule redevient vide, plus de joueur associé
    }
    public Object getPiece() {
        return piece;
    }
    public Player getPlayer() {
        return player;
    }
    public Position getPosition() {
        return position;
    }
    public String displayPiece() {
        return (piece != null) ? piece.toString() : " ";  // Affiche la pièce ou un espace si vide
    }
}

class Chess {
    private Cell[][] board;
    private Player[] players;
    private Player currentPlayer;
    private List<Object> whitePieces;  // Liste des pièces du joueur blanc
    private List<Object> blackPieces;  // Liste des pièces du joueur noir
    public void play() {
        while (true) {
            createPlayers();       // Demander aux joueurs de saisir leur nom
            initialiseBoard();      // Initialiser l'échiquier
            while (!isCheckMate()) {  // Tant qu'il n'y a pas de CheckMate
                printBoard();       // Afficher l'état actuel de l'échiquier
                String move;
                do {
                    move = askMove();  // Demander le coup au joueur
                } while (!isValidMove(move)); // Répéter si le coup est invalide
                updateBoard(move);    // Mettre à jour l'échiquier avec le nouveau coup
                switchPlayer();       // Changer de joueur
            }
        }
    }
    private void createPlayers() {
        Scanner scanner = new Scanner(System.in);
        players = new Player[2];
        System.out.println("Entrez le nom du joueur 1 (blanc): ");
        players[0] = new Player(scanner.nextLine(), 0); // Joueur blanc
        System.out.println("Entrez le nom du joueur 2 (noir): ");
        players[1] = new Player(scanner.nextLine(), 1); // Joueur noir
        currentPlayer = players[0]; // Le joueur blanc commence
    }
    private void initialiseBoard() {
        board = new Cell[8][8];
        // Initialiser toutes les cellules de l'échiquier
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Cell(new Position((char) ('a' + j), 8 - i));
            }
        }
        // Supposons que tu as deux joueurs, "player1" et "player2"
        Player player1 = new Player("Player 1", 0);  // Blanc
        Player player2 = new Player("Player 2", 1);  // Noir
        // Création des listes de pièces pour chaque joueur
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        // Placement des pièces blanches (ligne 1 et 2)
        Rook whiteRook1 = new Rook(new Position('a', 1), 0);
        Rook whiteRook2 = new Rook(new Position('h', 1), 0);
        whitePieces.add(whiteRook1);
        whitePieces.add(whiteRook2);
        board[0][0].placePiece(whiteRook1, player1);
        board[0][7].placePiece(whiteRook2, player1);
        Knight whiteKnight1 = new Knight(new Position('b', 1), 0);
        Knight whiteKnight2 = new Knight(new Position('g', 1), 0);
        whitePieces.add(whiteKnight1);
        whitePieces.add(whiteKnight2);
        board[0][1].placePiece(whiteKnight1, player1);
        board[0][6].placePiece(whiteKnight2, player1);
        Bishop whiteBishop1 = new Bishop(new Position('c', 1), 0);
        Bishop whiteBishop2 = new Bishop(new Position('f', 1), 0);
        whitePieces.add(whiteBishop1);
        whitePieces.add(whiteBishop2);
        board[0][2].placePiece(whiteBishop1, player1);
        board[0][5].placePiece(whiteBishop2, player1);
        Queen whiteQueen = new Queen(new Position('d', 1), 0);
        King whiteKing = new King(new Position('e', 1), 0);
        whitePieces.add(whiteQueen);
        whitePieces.add(whiteKing);
        board[0][3].placePiece(whiteQueen, player1);
        board[0][4].placePiece(whiteKing, player1);
        // Placement des pions blancs (ligne 2)
        for (int j = 0; j < 8; j++) {
            Pawn whitePawn = new Pawn(new Position((char) ('a' + j), 2), 0);
            whitePieces.add(whitePawn);
            board[1][j].placePiece(whitePawn, player1);
        }
        // Placement des pièces noires (ligne 8 et 7)
        Rook blackRook1 = new Rook(new Position('a', 8), 1);
        Rook blackRook2 = new Rook(new Position('h', 8), 1);
        blackPieces.add(blackRook1);
        blackPieces.add(blackRook2);
        board[7][0].placePiece(blackRook1, player2);
        board[7][7].placePiece(blackRook2, player2);
        Knight blackKnight1 = new Knight(new Position('b', 8), 1);
        Knight blackKnight2 = new Knight(new Position('g', 8), 1);
        blackPieces.add(blackKnight1);
        blackPieces.add(blackKnight2);
        board[7][1].placePiece(blackKnight1, player2);
        board[7][6].placePiece(blackKnight2, player2);
        Bishop blackBishop1 = new Bishop(new Position('c', 8), 1);
        Bishop blackBishop2 = new Bishop(new Position('f', 8), 1);
        blackPieces.add(blackBishop1);
        blackPieces.add(blackBishop2);
        board[7][2].placePiece(blackBishop1, player2);
        board[7][5].placePiece(blackBishop2, player2);
        Queen blackQueen = new Queen(new Position('d', 8), 1);
        King blackKing = new King(new Position('e', 8), 1);
        blackPieces.add(blackQueen);
        blackPieces.add(blackKing);
        board[7][3].placePiece(blackQueen, player2);
        board[7][4].placePiece(blackKing, player2);
        // Placement des pions noirs (ligne 7)
        for (int j = 0; j < 8; j++) {
            Pawn blackPawn = new Pawn(new Position((char) ('a' + j), 7), 1);
            blackPieces.add(blackPawn);
            board[6][j].placePiece(blackPawn, player2);
        }
        // Les pièces sont stockées dans les listes locales ici, donc on ne les associe plus aux joueurs via `setPieces`.
    }

    private void printBoard() {
        System.out.println("  a b c d e f g h"); // Afficher les lettres des colonnes
        for (int i = 7; i >= 0; i--) { // Inverser le sens pour afficher la ligne 8 en bas
            System.out.print((i + 1) + " "); // Afficher les numéros des lignes
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j].displayPiece() + " ");
            }
            System.out.println(" " + (i + 1)); // Afficher le numéro à droite aussi
        }
        System.out.println("  a b c d e f g h"); // Répéter en bas pour symétrie
    }

    private String askMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(currentPlayer.getName() + ", entrez votre coup (ex : Nb1 c3): ");
        return scanner.nextLine();
    }
    private Object findPieceByType(List<Object> pieces, String pieceType, Position position) {
        for (Object piece : pieces) {
            if (piece instanceof Rook && pieceType.equals("R")) {
                Rook rook = (Rook) piece;
                if (rook.getPosition().toString().equals(position.toString())) {
                    return rook;
                }
            } else if (piece instanceof Knight && pieceType.equals("N")) {
                Knight knight = (Knight) piece;
                if (knight.getPosition().toString().equals(position.toString())) {
                    return knight;
                }
            } else if (piece instanceof Bishop && pieceType.equals("B")) {
                Bishop bishop = (Bishop) piece;
                if (bishop.getPosition().toString().equals(position.toString())) {
                    return bishop;
                }
            } else if (piece instanceof Queen && pieceType.equals("Q")) {
                Queen queen = (Queen) piece;
                if (queen.getPosition().toString().equals(position.toString())) {
                    return queen;
                }
            } else if (piece instanceof King && pieceType.equals("K")) {
                King king = (King) piece;
                if (king.getPosition().toString().equals(position.toString())) {
                    return king;
                }
            } else if (piece instanceof Pawn && pieceType.equals("P")) {
                Pawn pawn = (Pawn) piece;
                if (pawn.getPosition().toString().equals(position.toString())) {
                    return pawn;
                }
            }
        }
        return null;  // Aucune pièce trouvée
    }

    private boolean isValidMove(String move) {
        // Vérifier le format de base : une pièce, une case de départ, une case d'arrivée
        String regex;
        regex = "[KQRBNP]([a-h][1-8]) ([a-h][1-8])";
        if (!move.matches(regex)) {
            System.out.println("Erreur : Le format du coup est incorrect. Utilisez le format suivant : Nb1 c3");
            return false; // Si le format est incorrect
        }
        // Extraire la pièce, la position de départ et la position d'arrivée
        char pieceChar = move.charAt(0);  // Premier caractère (K, Q, R, etc.)
        char startCol = move.charAt(1);  // Colonne de départ (ex : 'a')
        char startRowChar = move.charAt(2);  // Rangée de départ (ex : '1')
        int startRow = startRowChar - '0';    // Convertir '1' en 1, '2' en 2, etc.
        char endCol = move.charAt(4);
        char endRowChar = move.charAt(5);
        int endRow = endRowChar - '0';

        // Convertir les coordonnées en position sur l'échiquier
        Position start = new Position(startCol, startRow);
        Position end = new Position(endCol, endRow);
        // Rechercher la pièce dans la liste des pièces du joueur courant (whitePieces ou blackPieces)
        List<Object> pieces = (currentPlayer.getColor() == 0) ? whitePieces : blackPieces;
        // Utiliser findPieceByType pour trouver la pièce spécifique à cette position
        Object piece = findPieceByType(pieces, String.valueOf(pieceChar), start);
        if (piece == null) {
            return false; // Pas de pièce trouvée à la position de départ
        }
        // Vérifier si la pièce peut effectuer le mouvement en utilisant sa propre méthode isValidMove
        if (piece instanceof Rook) {
            return ((Rook) piece).isValidMove(end, board);
        } else if (piece instanceof Knight) {
            return ((Knight) piece).isValidMove(end, board);
        } else if (piece instanceof Bishop) {
            return ((Bishop) piece).isValidMove(end, board);
        } else if (piece instanceof Queen) {
            return ((Queen) piece).isValidMove(end, board);
        } else if (piece instanceof King) {
            return ((King) piece).isValidMove(end, board);
        } else if (piece instanceof Pawn) {
            return ((Pawn) piece).isValidMove(end, board);
        }
        return false; // Si le mouvement n'est pas valide pour la pièce
    }

    private void updateBoard(String move) {
        // Extraire les informations du mouvement : type de pièce, position de départ et position d'arrivée
        char pieceChar = move.charAt(0);  // Premier caractère
        char startCol = move.charAt(1);  // Colonne de départ
        char startRowChar = move.charAt(2);  // Rangée de départ
        int startRow = startRowChar - '0';
        int startColInt= startCol - 'a' +1;
        char endCol = move.charAt(4);    // Colonne d'arrivée
        char endRowChar = move.charAt(5);    // Rangée d'arrivée
        int endRow = endRowChar - '0';  // Convertir '3' en 3, '4'
        int endColInt= endCol- 'a' +1;
        // Convertir les coordonnées en position sur l'échiquier
        Position start = new Position(startCol, startRow);
        Position end = new Position(endCol, endRow);
        // Rechercher la pièce dans la liste des pièces du joueur courant
        List<Object> pieces = (currentPlayer.getColor() == 0) ? whitePieces : blackPieces;
        // Utiliser findPieceByType pour trouver la pièce spécifique à cette position
        Object piece = findPieceByType(pieces, String.valueOf(pieceChar), start);
        if (piece == null) {
            System.out.println("Aucune pièce trouvée à la position de départ");
            return; // Sortir si aucune pièce n'est trouvée à la position de départ
        }
        // Mettre à jour la position de la pièce sur le plateau
        //On convertie le Col en nombre
        Cell startCell = board[startRow-1][startColInt-1];
        Cell endCell = board[endRow-1][endColInt-1];
        // Supprimer la pièce de la case de départ
        startCell.removePiece();
        // Placer la pièce à la nouvelle position
        endCell.placePiece(piece, currentPlayer);  // Associer la pièce au joueur
        // Mettre à jour la position de la pièce
        if (piece instanceof Rook) {
            ((Rook) piece).setPosition(end);
        } else if (piece instanceof Knight) {
            ((Knight) piece).setPosition(end);
        } else if (piece instanceof Bishop) {
            ((Bishop) piece).setPosition(end);
        } else if (piece instanceof Queen) {
            ((Queen) piece).setPosition(end);
        } else if (piece instanceof King) {
            ((King) piece).setPosition(end);
        } else if (piece instanceof Pawn) {
            ((Pawn) piece).setPosition(end);
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == players[0]) ? players[1] : players[0];
    }
    private boolean isCheckMate() {
        return false;
    }
}
public class Main {
    public static void main(String[] args) {
        Chess chess = new Chess();
        chess.play();
    }
}
 
 
test
 
