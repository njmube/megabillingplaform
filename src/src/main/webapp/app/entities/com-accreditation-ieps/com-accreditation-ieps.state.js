(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-accreditation-ieps', {
            parent: 'entity',
            url: '/com-accreditation-ieps?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_accreditation_ieps.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-accreditation-ieps/com-accreditation-ieps.html',
                    controller: 'Com_accreditation_iepsController',
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
                    $translatePartialLoader.addPart('com_accreditation_ieps');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-accreditation-ieps-detail', {
            parent: 'entity',
            url: '/com-accreditation-ieps/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_accreditation_ieps.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-accreditation-ieps/com-accreditation-ieps-detail.html',
                    controller: 'Com_accreditation_iepsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_accreditation_ieps');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_accreditation_ieps', function($stateParams, Com_accreditation_ieps) {
                    return Com_accreditation_ieps.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-accreditation-ieps.new', {
            parent: 'com-accreditation-ieps',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-accreditation-ieps/com-accreditation-ieps-dialog.html',
                    controller: 'Com_accreditation_iepsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-accreditation-ieps', null, { reload: true });
                }, function() {
                    $state.go('com-accreditation-ieps');
                });
            }]
        })
        .state('com-accreditation-ieps.edit', {
            parent: 'com-accreditation-ieps',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-accreditation-ieps/com-accreditation-ieps-dialog.html',
                    controller: 'Com_accreditation_iepsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_accreditation_ieps', function(Com_accreditation_ieps) {
                            return Com_accreditation_ieps.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-accreditation-ieps', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-accreditation-ieps.delete', {
            parent: 'com-accreditation-ieps',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-accreditation-ieps/com-accreditation-ieps-delete-dialog.html',
                    controller: 'Com_accreditation_iepsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_accreditation_ieps', function(Com_accreditation_ieps) {
                            return Com_accreditation_ieps.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-accreditation-ieps', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
