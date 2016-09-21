(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('taxpayer-certificate', {
            parent: 'entity',
            url: '/taxpayer-certificate?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_certificate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-certificate/taxpayer-certificates.html',
                    controller: 'Taxpayer_certificateController',
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
                    $translatePartialLoader.addPart('taxpayer_certificate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('taxpayer-certificate-detail', {
            parent: 'entity',
            url: '/taxpayer-certificate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_certificate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-certificate/taxpayer-certificate-detail.html',
                    controller: 'Taxpayer_certificateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_certificate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Taxpayer_certificate', function($stateParams, Taxpayer_certificate) {
                    return Taxpayer_certificate.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('taxpayer-certificate.new', {
            parent: 'taxpayer-certificate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-certificate/taxpayer-certificate-dialog.html',
                    controller: 'Taxpayer_certificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                path_certificate: null,
                                filecertificate: null,
                                filecertificateContentType: null,
                                path_key: null,
                                filekey: null,
                                filekeyContentType: null,
                                number_certificate: null,
                                date_certificate: null,
                                rfc_certificate: null,
                                bussines_name_cert: null,
                                date_created_cert: null,
                                date_expiration_cert: null,
                                info_certificate: null,
                                valid_days_cert: null,
                                pass_certificate: null,
                                valid_certificate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('taxpayer-certificate', null, { reload: true });
                }, function() {
                    $state.go('taxpayer-certificate');
                });
            }]
        })
        .state('taxpayer-certificate.edit', {
            parent: 'taxpayer-certificate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-certificate/taxpayer-certificate-dialog.html',
                    controller: 'Taxpayer_certificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Taxpayer_certificate', function(Taxpayer_certificate) {
                            return Taxpayer_certificate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-certificate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taxpayer-certificate.delete', {
            parent: 'taxpayer-certificate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-certificate/taxpayer-certificate-delete-dialog.html',
                    controller: 'Taxpayer_certificateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Taxpayer_certificate', function(Taxpayer_certificate) {
                            return Taxpayer_certificate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-certificate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
