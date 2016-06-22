(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cfdi-states', {
            parent: 'entity',
            url: '/cfdi-states?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.cfdi_states.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi-states/cfdi-states.html',
                    controller: 'Cfdi_statesController',
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
                    $translatePartialLoader.addPart('cfdi_states');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cfdi-states-detail', {
            parent: 'entity',
            url: '/cfdi-states/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.cfdi_states.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi-states/cfdi-states-detail.html',
                    controller: 'Cfdi_statesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfdi_states');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cfdi_states', function($stateParams, Cfdi_states) {
                    return Cfdi_states.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('cfdi-states.new', {
            parent: 'cfdi-states',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-states/cfdi-states-dialog.html',
                    controller: 'Cfdi_statesDialogController',
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
                    $state.go('cfdi-states', null, { reload: true });
                }, function() {
                    $state.go('cfdi-states');
                });
            }]
        })
        .state('cfdi-states.edit', {
            parent: 'cfdi-states',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-states/cfdi-states-dialog.html',
                    controller: 'Cfdi_statesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cfdi_states', function(Cfdi_states) {
                            return Cfdi_states.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi-states', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cfdi-states.delete', {
            parent: 'cfdi-states',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-states/cfdi-states-delete-dialog.html',
                    controller: 'Cfdi_statesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cfdi_states', function(Cfdi_states) {
                            return Cfdi_states.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi-states', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
