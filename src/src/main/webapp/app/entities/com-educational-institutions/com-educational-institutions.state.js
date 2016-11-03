(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-educational-institutions', {
            parent: 'entity',
            url: '/com-educational-institutions?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_educational_institutions.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-educational-institutions/com-educational-institutions.html',
                    controller: 'Com_educational_institutionsController',
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
                    $translatePartialLoader.addPart('com_educational_institutions');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-educational-institutions-detail', {
            parent: 'entity',
            url: '/com-educational-institutions/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_educational_institutions.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-educational-institutions/com-educational-institutions-detail.html',
                    controller: 'Com_educational_institutionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_educational_institutions');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_educational_institutions', function($stateParams, Com_educational_institutions) {
                    return Com_educational_institutions.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-educational-institutions.new', {
            parent: 'com-educational-institutions',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-educational-institutions/com-educational-institutions-dialog.html',
                    controller: 'Com_educational_institutionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                name_student: null,
                                curp: null,
                                autrvoe: null,
                                rfcpayment: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-educational-institutions', null, { reload: true });
                }, function() {
                    $state.go('com-educational-institutions');
                });
            }]
        })
        .state('com-educational-institutions.edit', {
            parent: 'com-educational-institutions',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-educational-institutions/com-educational-institutions-dialog.html',
                    controller: 'Com_educational_institutionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_educational_institutions', function(Com_educational_institutions) {
                            return Com_educational_institutions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-educational-institutions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-educational-institutions.delete', {
            parent: 'com-educational-institutions',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-educational-institutions/com-educational-institutions-delete-dialog.html',
                    controller: 'Com_educational_institutionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_educational_institutions', function(Com_educational_institutions) {
                            return Com_educational_institutions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-educational-institutions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
