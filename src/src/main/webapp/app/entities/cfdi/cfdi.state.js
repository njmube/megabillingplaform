(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cfdi', {
            parent: 'entity',
            url: '/cfdi?page&sort&search',
            data: {
                authorities: ['ROLE_AFILITATED'],
                pageTitle: 'megabillingplatformApp.cfdi.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi/cfdis.html',
                    controller: 'CfdiController',
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
                    $translatePartialLoader.addPart('cfdi');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cfdi-new', {
            parent: 'entity',
            url: '/cfdi-new/{id}',
            data: {
                authorities: ['ROLE_AFILITATED'],
                pageTitle: 'megabillingplatformApp.cfdi.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi/cfdi-new.html',
                    controller: 'CfdiNewController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: [function () {
                    return {
                        version: null,
                        serial: null,
                        folio: null,
                        date_expedition: null,
                        payment_conditions: null,
                        change_type: (1).toFixed(2),
                        place_expedition: null,
                        account_number: null,
                        folio_fiscal_orig: null,
                        serial_folio_fiscal_orig: null,
                        date_folio_fiscal_orig: null,
                        mont_folio_fiscal_orig: null,
                        total_tax_retention: null,
                        total_tax_transfered: null,
                        discount: (0).toFixed(2),
                        discount_reason: null,
                        subtotal: (0).toFixed(2),
                        total: (0).toFixed(2),
                        addenda: null,
                        number_certificate: null,
                        certificate: null,
                        id: null
                    };
                }],
                taxpayer_account_entity: ['$stateParams', 'Taxpayer_account', function($stateParams, Taxpayer_account) {
                    return Taxpayer_account.get({id : $stateParams.id}).$promise;
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfdi');
                    $translatePartialLoader.addPart('taxpayer_account');
                    $translatePartialLoader.addPart('taxpayer_client');
                    $translatePartialLoader.addPart('client_address');
                    $translatePartialLoader.addPart('customs_info');
                    $translatePartialLoader.addPart('part_concept');
                    $translatePartialLoader.addPart('customs_info_part');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cfdi-detail', {
            parent: 'entity',
            url: '/cfdi/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.cfdi.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi/cfdi-detail.html',
                    controller: 'CfdiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfdi');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cfdi', function($stateParams, Cfdi) {
                    return Cfdi.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('cfdi.new', {
            parent: 'cfdi',
            url: '/new',
            data: {
                authorities: ['ROLE_AFILITATED']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi/cfdi-dialog.html',
                    controller: 'CfdiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                serial: null,
                                folio: null,
                                date_expedition: null,
                                payment_conditions: null,
                                change_type: null,
                                place_expedition: null,
                                account_number: null,
                                folio_fiscal_orig: null,
                                serial_folio_fiscal_orig: null,
                                date_folio_fiscal_orig: null,
                                mont_folio_fiscal_orig: null,
                                total_tax_retention: null,
                                total_tax_transfered: null,
                                discount: null,
                                discount_reason: null,
                                subtotal: null,
                                total: null,
                                addenda: null,
                                number_certificate: null,
                                certificate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cfdi', null, { reload: true });
                }, function() {
                    $state.go('cfdi');
                });
            }]
        })
        .state('cfdi.edit', {
            parent: 'cfdi',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi/cfdi-dialog.html',
                    controller: 'CfdiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cfdi', function(Cfdi) {
                            return Cfdi.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cfdi.delete', {
            parent: 'cfdi',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi/cfdi-delete-dialog.html',
                    controller: 'CfdiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cfdi', function(Cfdi) {
                            return Cfdi.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
