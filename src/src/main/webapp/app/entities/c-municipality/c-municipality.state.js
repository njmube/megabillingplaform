(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-municipality', {
            parent: 'entity',
            url: '/c-municipality?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_municipality.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-municipality/c-municipalities.html',
                    controller: 'C_municipalityController',
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
                    $translatePartialLoader.addPart('c_municipality');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-municipality-detail', {
            parent: 'entity',
            url: '/c-municipality/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_municipality.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-municipality/c-municipality-detail.html',
                    controller: 'C_municipalityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_municipality');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_municipality', function($stateParams, C_municipality) {
                    return C_municipality.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-municipality.new', {
            parent: 'c-municipality',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-municipality/c-municipality-dialog.html',
                    controller: 'C_municipalityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                code: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-municipality', null, { reload: true });
                }, function() {
                    $state.go('c-municipality');
                });
            }]
        })
        .state('c-municipality.edit', {
            parent: 'c-municipality',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-municipality/c-municipality-dialog.html',
                    controller: 'C_municipalityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_municipality', function(C_municipality) {
                            return C_municipality.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-municipality', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-municipality.delete', {
            parent: 'c-municipality',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-municipality/c-municipality-delete-dialog.html',
                    controller: 'C_municipalityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_municipality', function(C_municipality) {
                            return C_municipality.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-municipality', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
