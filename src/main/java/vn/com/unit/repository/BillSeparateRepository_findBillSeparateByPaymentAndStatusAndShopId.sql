declare @shop int = /*shop_id*/;

select bill_separate.*, bill.payment, bill.create_at
from p2p_bill_separate bill_separate
left join p2p_bill bill
on bill.id = bill_separate.bill
where bill_separate.shop = @shop
/*IF payment > 0 */
  and bill.payment > 0
  and bill_separate.status = /*status*/
/*END*/
/*IF payment < 0 */
  and bill.payment < 0
  and bill_separate.status = 0
  /*END*/
/*IF payment == 0 */
  and bill.payment = 0
    and bill_separate.status = 0
/*END*/  
order by bill_separate.id desc
OFFSET  /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY
