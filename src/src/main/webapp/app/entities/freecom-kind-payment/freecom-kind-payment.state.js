(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-kind-payment', {
            parent: 'entity',
            url: '/freecom-kind-payment?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_kind_payment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-kind-payment/freecom-kind-payments.html',
                    controller: 'Freecom_kind_paymentController',
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
                    $translatePartialLoader.addPart('freecom_kind_payment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-kind-payment-detail', {
            parent: 'entity',
            url: '/freecom-kind-payment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_kind_payment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-kind-payment/freecom-kind-payment-detail.html',
                    controller: 'Freecom_kind_paymentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_kind_payment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_kind_payment', function($stateParams, Freecom_kind_payment) {
                    return Freecom_kind_payment.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-kind-payment.new', {
            parent: 'freecom-kind-payment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-kind-payment/freecom-kind-payment-dialog.html',
                    controller: 'Freecom_kind_paymentDialogController',
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
                    $state.go('freecom-kind-payment', null, { reload: true });
                }, function() {
                    $state.go('freecom-kind-payment');
                });
            }]
        })
        .state('freecom-kind-payment.edit', {
            parent: 'freecom-kind-payment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-kind-payment/freecom-kind-payment-dialog.html',
                    controller: 'Freecom_kind_paymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_kind_payment', function(Freecom_kind_payment) {
                            return Freecom_kind_payment.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-kind-payment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-kind-payment.delete', {
            parent: 'freecom-kind-payment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-kind-payment/freecom-kind-payment-delete-dialog.html',
                    controller: 'Freecom_kind_paymentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_kind_payment', function(Freecom_kind_payment) {
                            return Freecom_kind_payment.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-kind-payment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
