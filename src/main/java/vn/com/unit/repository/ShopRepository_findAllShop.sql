SELECT S.*,A.name owner_name
FROM  p2p_shop S left Join p2p_account A on A.id=S.id
order by S.id
OFFSET  /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY