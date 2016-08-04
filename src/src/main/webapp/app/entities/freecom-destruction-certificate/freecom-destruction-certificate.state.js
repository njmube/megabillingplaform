(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-destruction-certificate', {
            parent: 'entity',
            url: '/freecom-destruction-certificate?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_destruction_certificate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-destruction-certificate/freecom-destruction-certificates.html',
                    controller: 'Freecom_destruction_certificateController',
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
                    $translatePartialLoader.addPart('freecom_destruction_certificate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-destruction-certificate-detail', {
            parent: 'entity',
            url: '/freecom-destruction-certificate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_destruction_certificate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-destruction-certificate/freecom-destruction-certificate-detail.html',
                    controller: 'Freecom_destruction_certificateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_destruction_certificate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_destruction_certificate', function($stateParams, Freecom_destruction_certificate) {
                    return Freecom_destruction_certificate.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-destruction-certificate.new', {
            parent: 'freecom-destruction-certificate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-destruction-certificate/freecom-destruction-certificate-dialog.html',
                    controller: 'Freecom_destruction_certificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                numfoldesveh: null,
                                brand: null,
                                class_dc: null,
                                year: null,
                                model: null,
                                niv: null,
                                no_serie: null,
                                number_plates: null,
                                number_engine: null,
                                numfoltarjcir: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-destruction-certificate', null, { reload: true });
                }, function() {
                    $state.go('freecom-destruction-certificate');
                });
            }]
        })
        .state('freecom-destruction-certificate.edit', {
            parent: 'freecom-destruction-certificate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-destruction-certificate/freecom-destruction-certificate-dialog.html',
                    controller: 'Freecom_destruction_certificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_destruction_certificate', function(Freecom_destruction_certificate) {
                            return Freecom_destruction_certificate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-destruction-certificate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-destruction-certificate.delete', {
            parent: 'freecom-destruction-certificate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-destruction-certificate/freecom-destruction-certificate-delete-dialog.html',
                    controller: 'Freecom_destruction_certificateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_destruction_certificate', function(Freecom_destruction_certificate) {
                            return Freecom_destruction_certificate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-destruction-certificate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
