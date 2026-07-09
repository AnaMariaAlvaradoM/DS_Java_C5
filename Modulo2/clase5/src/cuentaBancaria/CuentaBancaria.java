package cuentaBancaria;

public class CuentaBancaria {
    private String titular;
    private String numeroCuenta;
    private double saldo;

    public CuentaBancaria() {
        this.titular = "Sin asignar";
        this.numeroCuenta = "0000";
        this.saldo = 0.0;
    }

    public CuentaBancaria(String titular, String numeroCuenta, double saldoInicial) {
        this.titular = titular;
        this.numeroCuenta = numeroCuenta;
        if (saldoInicial >= 0) {
            this.saldo = saldoInicial;
        } else {
            this.saldo = 0.0;
        }
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            System.out.println("Error: el monto a depositar debe ser mayor a cero.");
            return;
        }
        saldo += monto;
        System.out.println("Deposito exitoso. Nuevo saldo: $" + saldo);
    }

    public void retirar(double monto) {
        if (monto <= 0) {
            System.out.println("Error: el monto a retirar debe ser mayor a cero.");
            return;
        }
        if (monto > saldo) {
            System.out.println("Error: fondos insuficientes. Saldo actual: $" + saldo);
            return;
        }
        saldo -= monto;
        System.out.println("Retiro exitoso. Nuevo saldo: $" + saldo);
    }


    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "saldo=" + saldo +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", titular='" + titular + '\'' +
                '}';
    }
}