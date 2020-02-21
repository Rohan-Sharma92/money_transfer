package com.revolut.moneytransfer.app.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.revolut.moneytransfer.handlers.AccountRequestHandler;
import com.revolut.moneytransfer.handlers.WalletRequestHandler;
import com.revolut.moneytransfer.handlers.MoneyTransferRequestHandler;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.model.IBalance;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.repo.impl.GenericRepository;
import com.revolut.moneytransfer.services.IAccountOperationService;
import com.revolut.moneytransfer.services.IMoneyTransferOperationService;
import com.revolut.moneytransfer.services.impl.AccountOperationService;
import com.revolut.moneytransfer.services.impl.MoneyTransferOperationService;
import com.revolut.moneytransfer.validation.AccountValidationRule;
import com.revolut.moneytransfer.validation.AmountValidationRule;
import com.revolut.moneytransfer.validation.DebitAccountValidationRule;
import com.revolut.moneytransfer.validation.IValidationRule;
import com.revolut.moneytransfer.validation.IValidationRuleEngine;
import com.revolut.moneytransfer.validation.ValidationRuleEngine;

public class MoneyTransferModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(new TypeLiteral<IGenericRepository<IAccount, String>>() {
		}).to(new TypeLiteral<GenericRepository<IAccount, String>>() {
		}).in(Scopes.SINGLETON);
		bind(new TypeLiteral<IGenericRepository<IBalance, String>>() {
		}).to(new TypeLiteral<GenericRepository<IBalance, String>>() {
		}).in(Scopes.SINGLETON);
		bind(IAccountOperationService.class).to(AccountOperationService.class).in(Scopes.SINGLETON);
		bind(IMoneyTransferOperationService.class).to(MoneyTransferOperationService.class).in(Scopes.SINGLETON);
		bind(AccountRequestHandler.class).in(Scopes.SINGLETON);
		bind(WalletRequestHandler.class).in(Scopes.SINGLETON);
		bind(MoneyTransferRequestHandler.class).in(Scopes.SINGLETON);
		bindValidationRules();
	}

	private void bindValidationRules() {
		Multibinder<IValidationRule> setBinder = Multibinder.newSetBinder(binder(), IValidationRule.class);
		setBinder.addBinding().to(AccountValidationRule.class);
		setBinder.addBinding().to(AmountValidationRule.class);
		setBinder.addBinding().to(DebitAccountValidationRule.class);
		bind(AccountValidationRule.class).in(Scopes.SINGLETON);
		bind(AmountValidationRule.class).in(Scopes.SINGLETON);
		bind(DebitAccountValidationRule.class).in(Scopes.SINGLETON);
		bind(IValidationRuleEngine.class).to(ValidationRuleEngine.class).in(Scopes.SINGLETON);
	}

}
