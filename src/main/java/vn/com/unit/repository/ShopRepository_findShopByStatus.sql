SELECT S.*,A.username
FROM  p2p_shop S left Join p2p_account A on A.id=S.id
where status=/*status*/
order by S.id
OFFSET  /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY