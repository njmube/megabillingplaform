(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-determined', {
            parent: 'entity',
            url: '/freecom-determined?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_determined.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-determined/freecom-determineds.html',
                    controller: 'Freecom_determinedController',
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
                    $translatePartialLoader.addPart('freecom_determined');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-determined-detail', {
            parent: 'entity',
            url: '/freecom-determined/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_determined.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-determined/freecom-determined-detail.html',
                    controller: 'Freecom_determinedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_determined');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_determined', function($stateParams, Freecom_determined) {
                    return Freecom_determined.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-determined.new', {
            parent: 'freecom-determined',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-determined/freecom-determined-dialog.html',
                    controller: 'Freecom_determinedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rate: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-determined', null, { reload: true });
                }, function() {
                    $state.go('freecom-determined');
                });
            }]
        })
        .state('freecom-determined.edit', {
            parent: 'freecom-determined',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-determined/freecom-determined-dialog.html',
                    controller: 'Freecom_determinedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_determined', function(Freecom_determined) {
                            return Freecom_determined.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-determined', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-determined.delete', {
            parent: 'freecom-determined',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-determined/freecom-determined-delete-dialog.html',
                    controller: 'Freecom_determinedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_determined', function(Freecom_determined) {
                            return Freecom_determined.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-determined', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
