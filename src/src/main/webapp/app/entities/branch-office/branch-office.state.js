(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('branch-office', {
            parent: 'entity',
            url: '/branch-office?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.branch_office.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/branch-office/branch-offices.html',
                    controller: 'Branch_officeController',
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
                    $translatePartialLoader.addPart('branch_office');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('branch-office-detail', {
            parent: 'entity',
            url: '/branch-office/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.branch_office.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/branch-office/branch-office-detail.html',
                    controller: 'Branch_officeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('branch_office');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Branch_office', function($stateParams, Branch_office) {
                    return Branch_office.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('branch-office.new', {
            parent: 'branch-office',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/branch-office/branch-office-dialog.html',
                    controller: 'Branch_officeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                bussines_name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('branch-office', null, { reload: true });
                }, function() {
                    $state.go('branch-office');
                });
            }]
        })
        .state('branch-office.edit', {
            parent: 'branch-office',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/branch-office/branch-office-dialog.html',
                    controller: 'Branch_officeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Branch_office', function(Branch_office) {
                            return Branch_office.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('branch-office', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('branch-office.delete', {
            parent: 'branch-office',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/branch-office/branch-office-delete-dialog.html',
                    controller: 'Branch_officeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Branch_office', function(Branch_office) {
                            return Branch_office.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('branch-office', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
