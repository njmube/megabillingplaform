(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-zip-code', {
            parent: 'entity',
            url: '/c-zip-code?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_zip_code.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-zip-code/c-zip-codes.html',
                    controller: 'C_zip_codeController',
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
                    $translatePartialLoader.addPart('c_zip_code');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-zip-code-detail', {
            parent: 'entity',
            url: '/c-zip-code/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_zip_code.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-zip-code/c-zip-code-detail.html',
                    controller: 'C_zip_codeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_zip_code');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_zip_code', function($stateParams, C_zip_code) {
                    return C_zip_code.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-zip-code.new', {
            parent: 'c-zip-code',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-zip-code/c-zip-code-dialog.html',
                    controller: 'C_zip_codeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-zip-code', null, { reload: true });
                }, function() {
                    $state.go('c-zip-code');
                });
            }]
        })
        .state('c-zip-code.edit', {
            parent: 'c-zip-code',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-zip-code/c-zip-code-dialog.html',
                    controller: 'C_zip_codeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_zip_code', function(C_zip_code) {
                            return C_zip_code.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-zip-code', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-zip-code.delete', {
            parent: 'c-zip-code',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-zip-code/c-zip-code-delete-dialog.html',
                    controller: 'C_zip_codeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_zip_code', function(C_zip_code) {
                            return C_zip_code.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-zip-code', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
