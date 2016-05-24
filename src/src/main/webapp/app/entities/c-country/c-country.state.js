(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-country', {
            parent: 'entity',
            url: '/c-country?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_country.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-country/c-countries.html',
                    controller: 'C_countryController',
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
                    $translatePartialLoader.addPart('c_country');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-country-detail', {
            parent: 'entity',
            url: '/c-country/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_country.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-country/c-country-detail.html',
                    controller: 'C_countryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_country');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_country', function($stateParams, C_country) {
                    return C_country.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-country.new', {
            parent: 'c-country',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-country/c-country-dialog.html',
                    controller: 'C_countryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                abrev: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-country', null, { reload: true });
                }, function() {
                    $state.go('c-country');
                });
            }]
        })
        .state('c-country.edit', {
            parent: 'c-country',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-country/c-country-dialog.html',
                    controller: 'C_countryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_country', function(C_country) {
                            return C_country.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-country', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-country.delete', {
            parent: 'c-country',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-country/c-country-delete-dialog.html',
                    controller: 'C_countryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_country', function(C_country) {
                            return C_country.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-country', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
