(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('taxpayer-request-confirm', {
            parent: 'entity',
            url: '/taxpayer-request-confirm?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_request_confirm.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-request-confirm/taxpayer-request-confirms.html',
                    controller: 'Taxpayer_request_confirmController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_request_confirm');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('taxpayer-request-confirm-detail', {
            parent: 'entity',
            url: '/taxpayer-request-confirm/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_request_confirm.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-request-confirm/taxpayer-request-confirm-detail.html',
                    controller: 'Taxpayer_request_confirmDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_request_confirm');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Taxpayer_request_confirm', function($stateParams, Taxpayer_request_confirm) {
                    return Taxpayer_request_confirm.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('taxpayer-request-confirm.new', {
            parent: 'taxpayer-request-confirm',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-request-confirm/taxpayer-request-confirm-dialog.html',
                    controller: 'Taxpayer_request_confirmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                taxpayer_request_id: null,
                                account_number_ach: null,
                                account_type_ach: null,
                                additional_value: null,
                                administrative_fee: null,
                                administrative_fee_base: null,
                                administrative_fee_tax: null,
                                airline_code: null,
                                attempts: null,
                                authorization_code: null,
                                bank_id: null,
                                billing_address: null,
                                billing_city: null,
                                billing_country: null,
                                commision_pol: null,
                                commision_pol_currency: null,
                                currency: null,
                                cus: null,
                                customer_number: null,
                                date: null,
                                description: null,
                                email_buyer: null,
                                error_code_bank: null,
                                error_message_bank: null,
                                exchange_rate: null,
                                extra1: null,
                                extra2: null,
                                installments_number: null,
                                ip: null,
                                merchant_id: null,
                                nickname_buyer: null,
                                nickname_seller: null,
                                office_phone: null,
                                payment_method: null,
                                payment_method_id: null,
                                payment_method_name: null,
                                payment_method_type: null,
                                payment_request_state: null,
                                phone: null,
                                pse_bank: null,
                                pse_reference1: null,
                                pse_reference2: null,
                                pse_reference3: null,
                                reference_pol: null,
                                reference_sale: null,
                                response_code_pol: null,
                                response_message_pol: null,
                                risk: null,
                                shipping_address: null,
                                shipping_city: null,
                                shipping_country: null,
                                sign: null,
                                state_pol: null,
                                tax: null,
                                test: null,
                                transaction_bank_id: null,
                                transactiondate: null,
                                transaction_id: null,
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('taxpayer-request-confirm', null, { reload: true });
                }, function() {
                    $state.go('taxpayer-request-confirm');
                });
            }]
        })
        .state('taxpayer-request-confirm.edit', {
            parent: 'taxpayer-request-confirm',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-request-confirm/taxpayer-request-confirm-dialog.html',
                    controller: 'Taxpayer_request_confirmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Taxpayer_request_confirm', function(Taxpayer_request_confirm) {
                            return Taxpayer_request_confirm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-request-confirm', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taxpayer-request-confirm.delete', {
            parent: 'taxpayer-request-confirm',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-request-confirm/taxpayer-request-confirm-delete-dialog.html',
                    controller: 'Taxpayer_request_confirmDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Taxpayer_request_confirm', function(Taxpayer_request_confirm) {
                            return Taxpayer_request_confirm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-request-confirm', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
