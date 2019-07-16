package com.warehouse.models;

import java.util.List;

public class TransactionsStatistics {
    private double totalDepositsCost;
    private int totalDepositsCount;
    private double totalWithdrawCost;
    private int totalWithdrawCount;


    public double getTotalDepositsCost() {
        return totalDepositsCost;
    }

    public int getTotalDepositsCount() {
        return totalDepositsCount;
    }

    public double getTotalWithdrawCost() {
        return totalWithdrawCost;
    }

    public int getTotalWithdrawCount() {
        return totalWithdrawCount;
    }

    public TransactionsStatistics(List<Transaction> transactions){
        this.totalDepositsCost = 0;
        this.totalDepositsCount = 0;
        this.totalWithdrawCost = 0;
        this.totalWithdrawCount = 0;

        for (Transaction transaction : transactions) {
            double cost = transaction.CalculateCost();
            int count = transaction.getCount();

            if (transaction.getType() == TransactionType.DEPOSIT){
                this.totalDepositsCost += cost;
                this.totalDepositsCount += count;
            }
            else {
                this.totalWithdrawCost += cost;
                this.totalWithdrawCount += count;
            }
        }
    }
}
