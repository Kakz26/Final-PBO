public class Ship implements ShipInterface {
    protected int x;
    protected int y;
    protected String shipSymbol;

    public Ship(int x, int y) {
        this.x = x;
        this.y = y;
        this.shipSymbol = " ";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getShipSymbol() {
        return shipSymbol;
    }

    public static class PlayerShip extends Ship {
        public PlayerShip(int x, int y) {
            super(x, y);
            shipSymbol = "@";
        }
    }

    public static class ComputerShip extends Ship {
        public ComputerShip(int x, int y) {
            super(x, y);
            shipSymbol = "x";
        }
    }
}