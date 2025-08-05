package com.shaohao.scaffold.service.crypto;


import com.shaohao.scaffold.dto.helius.SolanaTransactionDTO;

import java.math.BigDecimal;

public interface SolanaService {

    BigDecimal getBalance(String address);

    SolanaTransactionDTO getTransaction(String tx);

}
