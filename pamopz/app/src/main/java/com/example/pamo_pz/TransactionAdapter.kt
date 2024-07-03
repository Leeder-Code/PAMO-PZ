import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pamo_pz.Transaction
import com.example.pamo_pz.databinding.ItemTransactionBinding

class TransactionAdapter(private var transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

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

    fun updateTransactions(newTransactions: List<Transaction>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }

    fun setData(transactions: List<Transaction>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

}