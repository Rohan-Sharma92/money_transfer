package com.revolut.moneytransfer.app.guice;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.revolut.moneytransfer.handlers.AccountRequestHandler;
import com.revolut.moneytransfer.handlers.MoneyTransferRequestHandler;
import com.revolut.moneytransfer.handlers.WalletRequestHandler;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.model.IBalance;
import com.revolut.moneytransfer.repo.IBalanceRepository;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.repo.impl.BalanceRepository;
import com.revolut.moneytransfer.repo.impl.GenericRepository;
import com.revolut.moneytransfer.services.IAccountOperationService;
import com.revolut.moneytransfer.services.IMoneyTransferOperationService;
import com.revolut.moneytransfer.services.impl.AccountOperationService;
import com.revolut.moneytransfer.services.impl.MoneyTransferOperationService;
import com.revolut.moneytransfer.validation.IValidationRule;
import com.revolut.moneytransfer.validation.IValidationRuleEngine;
import com.revolut.moneytransfer.validation.impl.AccountValidationRule;
import com.revolut.moneytransfer.validation.impl.AmountValidationRule;
import com.revolut.moneytransfer.validation.impl.DebitAccountBalanceValidationRule;
import com.revolut.moneytransfer.validation.impl.DebitAccountValidationRule;
import com.revolut.moneytransfer.validation.impl.ValidationRuleEngine;

public class MoneyTransferModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(new TypeLiteral<IGenericRepository<IAccount, String>>() {
		}).to(new TypeLiteral<GenericRepository<IAccount, String>>() {
		}).in(Scopes.SINGLETON);
		bind(IBalanceRepository.class).to(BalanceRepository.class).in(Scopes.SINGLETON);
		bind(IAccountOperationService.class).to(AccountOperationService.class).in(Scopes.SINGLETON);
		bind(IMoneyTransferOperationService.class).to(MoneyTransferOperationService.class).in(Scopes.SINGLETON);
		bind(AccountRequestHandler.class).in(Scopes.SINGLETON);
		bind(WalletRequestHandler.class).in(Scopes.SINGLETON);
		bind(MoneyTransferRequestHandler.class).in(Scopes.SINGLETON);
		bindValidationRules();
	}

	private void bindValidationRules() {
		bind(AccountValidationRule.class).in(Scopes.SINGLETON);
		bind(AmountValidationRule.class).in(Scopes.SINGLETON);
		bind(DebitAccountValidationRule.class).in(Scopes.SINGLETON);
		bind(DebitAccountBalanceValidationRule.class).in(Scopes.SINGLETON);
		bind(IValidationRuleEngine.class).to(ValidationRuleEngine.class).in(Scopes.SINGLETON);
	}

	@Provides
	@Singleton
	public List<IValidationRule> getRules(final AccountValidationRule accountValidationRule,
			final AmountValidationRule amountValidationRule,
			final DebitAccountValidationRule debitAccountValidationRule,
			final DebitAccountBalanceValidationRule debitAccountBalanceValidationRule) {
		return Lists.newArrayList(accountValidationRule, amountValidationRule, debitAccountValidationRule,
				debitAccountBalanceValidationRule);
	}
}
