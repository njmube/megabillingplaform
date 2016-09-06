(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-local-retentions', {
            parent: 'entity',
            url: '/freecom-local-retentions?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_local_retentions.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-local-retentions/freecom-local-retentions.html',
                    controller: 'Freecom_local_retentionsController',
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
                    $translatePartialLoader.addPart('freecom_local_retentions');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-local-retentions-detail', {
            parent: 'entity',
            url: '/freecom-local-retentions/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_local_retentions.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-local-retentions/freecom-local-retentions-detail.html',
                    controller: 'Freecom_local_retentionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_local_retentions');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_local_retentions', function($stateParams, Freecom_local_retentions) {
                    return Freecom_local_retentions.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-local-retentions.new', {
            parent: 'freecom-local-retentions',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-local-retentions/freecom-local-retentions-dialog.html',
                    controller: 'Freecom_local_retentionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                implocretentions: null,
                                retentionrate: null,
                                amountretentions: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-local-retentions', null, { reload: true });
                }, function() {
                    $state.go('freecom-local-retentions');
                });
            }]
        })
        .state('freecom-local-retentions.edit', {
            parent: 'freecom-local-retentions',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-local-retentions/freecom-local-retentions-dialog.html',
                    controller: 'Freecom_local_retentionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_local_retentions', function(Freecom_local_retentions) {
                            return Freecom_local_retentions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-local-retentions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-local-retentions.delete', {
            parent: 'freecom-local-retentions',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-local-retentions/freecom-local-retentions-delete-dialog.html',
                    controller: 'Freecom_local_retentionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_local_retentions', function(Freecom_local_retentions) {
                            return Freecom_local_retentions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-local-retentions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
