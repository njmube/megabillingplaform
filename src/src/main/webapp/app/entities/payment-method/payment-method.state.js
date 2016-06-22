(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('payment-method', {
            parent: 'entity',
            url: '/payment-method?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.payment_method.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payment-method/payment-methods.html',
                    controller: 'Payment_methodController',
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
                    $translatePartialLoader.addPart('payment_method');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('payment-method-detail', {
            parent: 'entity',
            url: '/payment-method/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.payment_method.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payment-method/payment-method-detail.html',
                    controller: 'Payment_methodDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('payment_method');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Payment_method', function($stateParams, Payment_method) {
                    return Payment_method.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('payment-method.new', {
            parent: 'payment-method',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-method/payment-method-dialog.html',
                    controller: 'Payment_methodDialogController',
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
                    $state.go('payment-method', null, { reload: true });
                }, function() {
                    $state.go('payment-method');
                });
            }]
        })
        .state('payment-method.edit', {
            parent: 'payment-method',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-method/payment-method-dialog.html',
                    controller: 'Payment_methodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Payment_method', function(Payment_method) {
                            return Payment_method.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payment-method', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payment-method.delete', {
            parent: 'payment-method',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-method/payment-method-delete-dialog.html',
                    controller: 'Payment_methodDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Payment_method', function(Payment_method) {
                            return Payment_method.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payment-method', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
