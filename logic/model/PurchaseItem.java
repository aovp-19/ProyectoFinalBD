package logic.model;

public class PurchaseItem {
    
	private int purchaseId;
    private int itemId;
    private int quantity;
    // + getters/setters

    public PurchaseItem(int purchaseId, int itemId, int quantity) {
        this.setPurchaseId(purchaseId);
        this.setItemId(itemId);
        this.setQuantity(quantity);
    }

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
}
