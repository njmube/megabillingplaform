(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-accreditation-ieps', {
            parent: 'entity',
            url: '/freecom-accreditation-ieps?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_accreditation_ieps.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-accreditation-ieps/freecom-accreditation-ieps.html',
                    controller: 'Freecom_accreditation_iepsController',
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
                    $translatePartialLoader.addPart('freecom_accreditation_ieps');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-accreditation-ieps-detail', {
            parent: 'entity',
            url: '/freecom-accreditation-ieps/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_accreditation_ieps.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-accreditation-ieps/freecom-accreditation-ieps-detail.html',
                    controller: 'Freecom_accreditation_iepsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_accreditation_ieps');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_accreditation_ieps', function($stateParams, Freecom_accreditation_ieps) {
                    return Freecom_accreditation_ieps.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-accreditation-ieps.new', {
            parent: 'freecom-accreditation-ieps',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-accreditation-ieps/freecom-accreditation-ieps-dialog.html',
                    controller: 'Freecom_accreditation_iepsDialogController',
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
                    $state.go('freecom-accreditation-ieps', null, { reload: true });
                }, function() {
                    $state.go('freecom-accreditation-ieps');
                });
            }]
        })
        .state('freecom-accreditation-ieps.edit', {
            parent: 'freecom-accreditation-ieps',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-accreditation-ieps/freecom-accreditation-ieps-dialog.html',
                    controller: 'Freecom_accreditation_iepsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_accreditation_ieps', function(Freecom_accreditation_ieps) {
                            return Freecom_accreditation_ieps.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-accreditation-ieps', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-accreditation-ieps.delete', {
            parent: 'freecom-accreditation-ieps',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-accreditation-ieps/freecom-accreditation-ieps-delete-dialog.html',
                    controller: 'Freecom_accreditation_iepsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_accreditation_ieps', function(Freecom_accreditation_ieps) {
                            return Freecom_accreditation_ieps.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-accreditation-ieps', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
