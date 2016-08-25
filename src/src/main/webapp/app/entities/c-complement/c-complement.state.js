(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-complement', {
            parent: 'entity',
            url: '/c-complement?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_complement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-complement/c-complements.html',
                    controller: 'C_complementController',
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
                    $translatePartialLoader.addPart('c_complement');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-complement-detail', {
            parent: 'entity',
            url: '/c-complement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_complement.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-complement/c-complement-detail.html',
                    controller: 'C_complementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_complement');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_complement', function($stateParams, C_complement) {
                    return C_complement.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('c-complement.new', {
            parent: 'c-complement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-complement/c-complement-dialog.html',
                    controller: 'C_complementDialogController',
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
                    $state.go('c-complement', null, { reload: true });
                }, function() {
                    $state.go('c-complement');
                });
            }]
        })
        .state('c-complement.edit', {
            parent: 'c-complement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-complement/c-complement-dialog.html',
                    controller: 'C_complementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_complement', function(C_complement) {
                            return C_complement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-complement', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-complement.delete', {
            parent: 'c-complement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-complement/c-complement-delete-dialog.html',
                    controller: 'C_complementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_complement', function(C_complement) {
                            return C_complement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-complement', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
