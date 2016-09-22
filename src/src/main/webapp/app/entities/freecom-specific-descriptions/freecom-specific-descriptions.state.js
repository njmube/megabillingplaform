(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-specific-descriptions', {
            parent: 'entity',
            url: '/freecom-specific-descriptions?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_specific_descriptions.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-specific-descriptions/freecom-specific-descriptions.html',
                    controller: 'Freecom_specific_descriptionsController',
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
                    $translatePartialLoader.addPart('freecom_specific_descriptions');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-specific-descriptions-detail', {
            parent: 'entity',
            url: '/freecom-specific-descriptions/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_specific_descriptions.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-specific-descriptions/freecom-specific-descriptions-detail.html',
                    controller: 'Freecom_specific_descriptionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_specific_descriptions');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_specific_descriptions', function($stateParams, Freecom_specific_descriptions) {
                    return Freecom_specific_descriptions.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-specific-descriptions.new', {
            parent: 'freecom-specific-descriptions',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-specific-descriptions/freecom-specific-descriptions-dialog.html',
                    controller: 'Freecom_specific_descriptionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                brand: null,
                                model: null,
                                submodel: null,
                                serial_number: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-specific-descriptions', null, { reload: true });
                }, function() {
                    $state.go('freecom-specific-descriptions');
                });
            }]
        })
        .state('freecom-specific-descriptions.edit', {
            parent: 'freecom-specific-descriptions',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-specific-descriptions/freecom-specific-descriptions-dialog.html',
                    controller: 'Freecom_specific_descriptionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_specific_descriptions', function(Freecom_specific_descriptions) {
                            return Freecom_specific_descriptions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-specific-descriptions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-specific-descriptions.delete', {
            parent: 'freecom-specific-descriptions',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-specific-descriptions/freecom-specific-descriptions-delete-dialog.html',
                    controller: 'Freecom_specific_descriptionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_specific_descriptions', function(Freecom_specific_descriptions) {
                            return Freecom_specific_descriptions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-specific-descriptions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
