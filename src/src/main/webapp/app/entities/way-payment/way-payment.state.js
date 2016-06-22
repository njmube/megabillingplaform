(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('way-payment', {
            parent: 'entity',
            url: '/way-payment?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.way_payment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/way-payment/way-payments.html',
                    controller: 'Way_paymentController',
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
                    $translatePartialLoader.addPart('way_payment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('way-payment-detail', {
            parent: 'entity',
            url: '/way-payment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.way_payment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/way-payment/way-payment-detail.html',
                    controller: 'Way_paymentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('way_payment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Way_payment', function($stateParams, Way_payment) {
                    return Way_payment.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('way-payment.new', {
            parent: 'way-payment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/way-payment/way-payment-dialog.html',
                    controller: 'Way_paymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('way-payment', null, { reload: true });
                }, function() {
                    $state.go('way-payment');
                });
            }]
        })
        .state('way-payment.edit', {
            parent: 'way-payment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/way-payment/way-payment-dialog.html',
                    controller: 'Way_paymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Way_payment', function(Way_payment) {
                            return Way_payment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('way-payment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('way-payment.delete', {
            parent: 'way-payment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/way-payment/way-payment-delete-dialog.html',
                    controller: 'Way_paymentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Way_payment', function(Way_payment) {
                            return Way_payment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('way-payment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
