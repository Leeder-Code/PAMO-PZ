import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pamo_pz.Transaction
import com.example.pamo_pz.databinding.ItemTransactionBinding

/**
 * Adapter for displaying a list of transactions in a RecyclerView.
 *
 * @param transactions List of transactions to display.
 */
class TransactionAdapter(private var transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * ViewHolder for each transaction item.
         *
         * @param binding View binding object for the transaction item layout.
         */
        fun bind(transaction: Transaction) {
            binding.textViewTransactionName.text = transaction.name
            binding.textViewTransactionDate.text = transaction.month
            binding.textViewTransactionCategory.text = transaction.category
            binding.textViewTransactionAmount.text = String.format("$%.2f", transaction.amount)


            // Set the color based on whether the amount is income or expense
            if (transaction.isIncome) {
                binding.textViewTransactionAmount.setTextColor(
                    binding.root.context.getColor(android.R.color.holo_green_dark)
                )
            } else {
                binding.textViewTransactionAmount.setTextColor(
                    binding.root.context.getColor(android.R.color.holo_red_dark)
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size
    /**
     * Updates the list of transactions with new data.
     *
     * @param newTransactions New list of transactions to display.
     */
    fun updateTransactions(newTransactions: List<Transaction>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }
    /**
     * Sets the initial data for the adapter.
     *
     * @param transactions List of transactions to initially display.
     */
    fun setData(transactions: List<Transaction>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

}