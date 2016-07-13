(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-educational-institutions', {
            parent: 'entity',
            url: '/freecom-educational-institutions?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_educational_institutions.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-educational-institutions/freecom-educational-institutions.html',
                    controller: 'Freecom_educational_institutionsController',
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
                    $translatePartialLoader.addPart('freecom_educational_institutions');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-educational-institutions-detail', {
            parent: 'entity',
            url: '/freecom-educational-institutions/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_educational_institutions.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-educational-institutions/freecom-educational-institutions-detail.html',
                    controller: 'Freecom_educational_institutionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_educational_institutions');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_educational_institutions', function($stateParams, Freecom_educational_institutions) {
                    return Freecom_educational_institutions.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-educational-institutions.new', {
            parent: 'freecom-educational-institutions',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-educational-institutions/freecom-educational-institutions-dialog.html',
                    controller: 'Freecom_educational_institutionsDialogController',
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
                    $state.go('freecom-educational-institutions', null, { reload: true });
                }, function() {
                    $state.go('freecom-educational-institutions');
                });
            }]
        })
        .state('freecom-educational-institutions.edit', {
            parent: 'freecom-educational-institutions',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-educational-institutions/freecom-educational-institutions-dialog.html',
                    controller: 'Freecom_educational_institutionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_educational_institutions', function(Freecom_educational_institutions) {
                            return Freecom_educational_institutions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-educational-institutions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-educational-institutions.delete', {
            parent: 'freecom-educational-institutions',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-educational-institutions/freecom-educational-institutions-delete-dialog.html',
                    controller: 'Freecom_educational_institutionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_educational_institutions', function(Freecom_educational_institutions) {
                            return Freecom_educational_institutions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-educational-institutions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
