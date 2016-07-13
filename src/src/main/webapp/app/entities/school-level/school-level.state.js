(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('school-level', {
            parent: 'entity',
            url: '/school-level?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.school_level.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/school-level/school-levels.html',
                    controller: 'School_levelController',
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
                    $translatePartialLoader.addPart('school_level');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('school-level-detail', {
            parent: 'entity',
            url: '/school-level/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.school_level.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/school-level/school-level-detail.html',
                    controller: 'School_levelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('school_level');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'School_level', function($stateParams, School_level) {
                    return School_level.get({id : $stateParams.id});
                }]
            }
        })
        .state('school-level.new', {
            parent: 'school-level',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/school-level/school-level-dialog.html',
                    controller: 'School_levelDialogController',
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
                    $state.go('school-level', null, { reload: true });
                }, function() {
                    $state.go('school-level');
                });
            }]
        })
        .state('school-level.edit', {
            parent: 'school-level',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/school-level/school-level-dialog.html',
                    controller: 'School_levelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['School_level', function(School_level) {
                            return School_level.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('school-level', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('school-level.delete', {
            parent: 'school-level',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/school-level/school-level-delete-dialog.html',
                    controller: 'School_levelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['School_level', function(School_level) {
                            return School_level.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('school-level', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
