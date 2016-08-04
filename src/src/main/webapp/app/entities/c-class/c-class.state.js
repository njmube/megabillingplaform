(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-class', {
            parent: 'entity',
            url: '/c-class?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_class.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-class/c-classes.html',
                    controller: 'C_classController',
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
                    $translatePartialLoader.addPart('c_class');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-class-detail', {
            parent: 'entity',
            url: '/c-class/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_class.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-class/c-class-detail.html',
                    controller: 'C_classDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_class');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_class', function($stateParams, C_class) {
                    return C_class.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-class.new', {
            parent: 'c-class',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-class/c-class-dialog.html',
                    controller: 'C_classDialogController',
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
                    $state.go('c-class', null, { reload: true });
                }, function() {
                    $state.go('c-class');
                });
            }]
        })
        .state('c-class.edit', {
            parent: 'c-class',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-class/c-class-dialog.html',
                    controller: 'C_classDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_class', function(C_class) {
                            return C_class.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-class', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-class.delete', {
            parent: 'c-class',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-class/c-class-delete-dialog.html',
                    controller: 'C_classDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_class', function(C_class) {
                            return C_class.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-class', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
