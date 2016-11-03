(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-kind-payment', {
            parent: 'entity',
            url: '/com-kind-payment?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_kind_payment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-kind-payment/com-kind-payments.html',
                    controller: 'Com_kind_paymentController',
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
                    $translatePartialLoader.addPart('com_kind_payment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-kind-payment-detail', {
            parent: 'entity',
            url: '/com-kind-payment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_kind_payment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-kind-payment/com-kind-payment-detail.html',
                    controller: 'Com_kind_paymentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_kind_payment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_kind_payment', function($stateParams, Com_kind_payment) {
                    return Com_kind_payment.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-kind-payment.new', {
            parent: 'com-kind-payment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-kind-payment/com-kind-payment-dialog.html',
                    controller: 'Com_kind_paymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                cvepic: null,
                                foliosoldon: null,
                                art_piece_name: null,
                                technical_art_piece: null,
                                year_art_piece: null,
                                dimensional_art_piece: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-kind-payment', null, { reload: true });
                }, function() {
                    $state.go('com-kind-payment');
                });
            }]
        })
        .state('com-kind-payment.edit', {
            parent: 'com-kind-payment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-kind-payment/com-kind-payment-dialog.html',
                    controller: 'Com_kind_paymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_kind_payment', function(Com_kind_payment) {
                            return Com_kind_payment.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-kind-payment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-kind-payment.delete', {
            parent: 'com-kind-payment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-kind-payment/com-kind-payment-delete-dialog.html',
                    controller: 'Com_kind_paymentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_kind_payment', function(Com_kind_payment) {
                            return Com_kind_payment.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-kind-payment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
