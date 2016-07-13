(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-charge', {
            parent: 'entity',
            url: '/freecom-charge?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_charge.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-charge/freecom-charges.html',
                    controller: 'Freecom_chargeController',
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
                    $translatePartialLoader.addPart('freecom_charge');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-charge-detail', {
            parent: 'entity',
            url: '/freecom-charge/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_charge.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-charge/freecom-charge-detail.html',
                    controller: 'Freecom_chargeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_charge');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_charge', function($stateParams, Freecom_charge) {
                    return Freecom_charge.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-charge.new', {
            parent: 'freecom-charge',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-charge/freecom-charge-dialog.html',
                    controller: 'Freecom_chargeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codecharge: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-charge', null, { reload: true });
                }, function() {
                    $state.go('freecom-charge');
                });
            }]
        })
        .state('freecom-charge.edit', {
            parent: 'freecom-charge',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-charge/freecom-charge-dialog.html',
                    controller: 'Freecom_chargeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_charge', function(Freecom_charge) {
                            return Freecom_charge.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-charge', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-charge.delete', {
            parent: 'freecom-charge',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-charge/freecom-charge-delete-dialog.html',
                    controller: 'Freecom_chargeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_charge', function(Freecom_charge) {
                            return Freecom_charge.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-charge', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
