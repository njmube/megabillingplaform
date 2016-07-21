(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-committee-type', {
            parent: 'entity',
            url: '/c-committee-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_committee_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-committee-type/c-committee-types.html',
                    controller: 'C_committee_typeController',
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
                    $translatePartialLoader.addPart('c_committee_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-committee-type-detail', {
            parent: 'entity',
            url: '/c-committee-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_committee_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-committee-type/c-committee-type-detail.html',
                    controller: 'C_committee_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_committee_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_committee_type', function($stateParams, C_committee_type) {
                    return C_committee_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-committee-type.new', {
            parent: 'c-committee-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-committee-type/c-committee-type-dialog.html',
                    controller: 'C_committee_typeDialogController',
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
                    $state.go('c-committee-type', null, { reload: true });
                }, function() {
                    $state.go('c-committee-type');
                });
            }]
        })
        .state('c-committee-type.edit', {
            parent: 'c-committee-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-committee-type/c-committee-type-dialog.html',
                    controller: 'C_committee_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_committee_type', function(C_committee_type) {
                            return C_committee_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-committee-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-committee-type.delete', {
            parent: 'c-committee-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-committee-type/c-committee-type-delete-dialog.html',
                    controller: 'C_committee_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_committee_type', function(C_committee_type) {
                            return C_committee_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-committee-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
