(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-incoterm', {
            parent: 'entity',
            url: '/freecom-incoterm?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_incoterm.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-incoterm/freecom-incoterms.html',
                    controller: 'Freecom_incotermController',
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
                    $translatePartialLoader.addPart('freecom_incoterm');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-incoterm-detail', {
            parent: 'entity',
            url: '/freecom-incoterm/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_incoterm.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-incoterm/freecom-incoterm-detail.html',
                    controller: 'Freecom_incotermDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_incoterm');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_incoterm', function($stateParams, Freecom_incoterm) {
                    return Freecom_incoterm.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-incoterm.new', {
            parent: 'freecom-incoterm',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-incoterm/freecom-incoterm-dialog.html',
                    controller: 'Freecom_incotermDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-incoterm', null, { reload: true });
                }, function() {
                    $state.go('freecom-incoterm');
                });
            }]
        })
        .state('freecom-incoterm.edit', {
            parent: 'freecom-incoterm',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-incoterm/freecom-incoterm-dialog.html',
                    controller: 'Freecom_incotermDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_incoterm', function(Freecom_incoterm) {
                            return Freecom_incoterm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-incoterm', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-incoterm.delete', {
            parent: 'freecom-incoterm',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-incoterm/freecom-incoterm-delete-dialog.html',
                    controller: 'Freecom_incotermDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_incoterm', function(Freecom_incoterm) {
                            return Freecom_incoterm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-incoterm', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
